import { Customer } from './../model/customer';
import { CustomerService } from './../service/customer-service';
import { Component, OnInit } from '@angular/core';
import { ColDef, GridApi, ColumnApi } from 'ag-grid-community';
import { NgForm } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-customer',
  templateUrl: './customer.component.html',
  styleUrls: ['./customer.component.css']
})

export class CustomerComponent implements OnInit {

  public customers: Customer[] = [];
  public columnDefs: ColDef[] = [];
  private api: GridApi = new GridApi;
  private columnApi: ColumnApi = new ColumnApi;
  private action?: string;
  public customer: Customer = new Customer;

  public defaultColDef: ColDef = {
    editable: false,
    sortable: true,
    flex: 1,
    minWidth: 100,
    filter: true,
    resizable: true,
  };

  constructor(private customerService: CustomerService) {
    this.columnDefs = this.createColumnDefs();
  }

  ngOnInit(): void {
    this.findAllCustomers();
  }

  onGridReady(params: any): void {
    this.api = params.api;
    this.columnApi = params.columnApi;
    this.api.sizeColumnsToFit();
    this.api.setDomLayout("autoHeight");
}

  private createColumnDefs() {
    return [
      {
       headerName: "customerId",
       field: "customerId",
       hide: true
    },{
        headerName: 'First Name',
        field: 'firstName',
        filter: true,
        sortable: true
    }, {
        headerName: 'Last Name',
        field: 'lastName',
        filter: true,
        sortable: true
    }, {
        headerName: 'Birthdate',
        field: 'birthDate',
        filter: true,
        sortable: true
    }
    ]
  }



  public findAllCustomers():void{
    this.customerService.findAllCustomer().subscribe(
      (response: Customer[]) => {
        this.customers = response;
      }
    );
  }

  public saveCustomer(addForm: NgForm): void {
    this.customer.customerId = addForm.value.customerId;
    this.customer.firstName = addForm.value.firstName;
    this.customer.lastName = addForm.value.lastName;
    this.customer.birthDate = addForm.value.birthDate;

    if(this.action == "add"){
      this.addCustomer(this.customer);
    }else{
      this.updateCustomer(this.customer);
    }
    addForm.reset();
  }

  public addCustomer(customer: Customer): void {
    this.customerService.saveCustomer(customer).subscribe(
      () => {
        this.findAllCustomers();
      },(error: HttpErrorResponse) => {
        alert(error.message);
      }
    );

  }

  public updateCustomer(customer: Customer): void {
    this.customerService.updateCustomer(this.customer).subscribe(
      () => {
        this.findAllCustomers();
        this.api.refreshCells();
      },(error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public deleteCustomer(): void {
    var selectedRows = this.api.getSelectedRows();
    if (selectedRows.length == 0) {
      alert("Please select a customer");
      return;
    }
    if (confirm("Are you sure you want to delete?")) {
      this.customerService.deleteCustomerById(selectedRows[0].customerId).subscribe(data => {
        this.findAllCustomers();
        this.api.refreshCells();
      });
    }
  }

  setAction(action: string){
    this.action = action;
  }

  setContract(): void {
    var selectedRow = this.api.getSelectedRows()[0];
    if (selectedRow == null) {
      alert("Please select a customer");
      return;
    }
    console.log(selectedRow);
    this.customer = new Customer(
      selectedRow.customerId,
      selectedRow.firstName,
      selectedRow.lastName,
      selectedRow.birthDate
    )
  }
}
