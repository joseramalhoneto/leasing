import { Vehicle } from './../model/vehicle';
import { Customer } from './../model/customer';
import { CustomerService } from './../service/customer-service';
import { VehicleService } from './../service/vehicle-service';
import { ContractService } from './../service/contract-service';
import { Contract } from './../model/contract';
import { Component, OnInit } from '@angular/core';
import { ColDef, GridApi, ColumnApi } from 'ag-grid-community';
import { NgForm } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-contract',
  templateUrl: './contract.component.html',
  styleUrls: ['./contract.component.css']
})
export class ContractComponent implements OnInit {

  public contracts: Contract[] = [];
  public customers: Customer[] = [];
  public vehicles: Vehicle[] = [];
  public columnDefs: ColDef[] = [];
  private api: GridApi = new GridApi;
  private columnApi: ColumnApi = new ColumnApi;
  public contract: Contract = new Contract();
  private action?: string;

  public defaultColDef: ColDef = {
    editable: false,
    sortable: true,
    flex: 1,
    minWidth: 100,
    filter: true,
    resizable: true,
  };

  constructor(private contractService: ContractService,
              private vehicleService: VehicleService,
              private customerService: CustomerService) {
    this.columnDefs = this.createColumnDefs();
  }

  ngOnInit(): void {
    this.findAllContracts();
    this.findAllCustomers();
    this.findAllVehicles();
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
       headerName: "contractId",
       field: "contractId",
       hide: true
    },{
        headerName: 'Contract No',
        field: 'contractNumber',
        filter: true,
        sortable: true
    },{
        headerName: 'Monthly Rate',
        field: 'monthlyRate',
        filter: true,
        sortable: true
    },{
        headerName: 'Customer',
        field: 'customer',
        filter: true,
        sortable: true,
        valueGetter:function(params: any){
          return params.data.customer.firstName + " " + params.data.customer.lastName
        }
    },{
        headerName: 'Vehicle',
        field: 'vehicle',
        filter: true,
        sortable: true,
        valueGetter:function(params: any){
          return params.data.vehicle.brand + " " + params.data.vehicle.model + " (" + params.data.vehicle.year + ")"
        }
    },{
        headerName: 'VIN',
        field: 'vehicle.vin',
        filter: true,
        valueGetter:function(params: any){
          if(params.data.vehicle.vin === "" || params.data.vehicle.vin === null)
            return " - ";
          else
            return params.data.vehicle.vin;
       }
    }
    ]
  }

  public findAllContracts():void{
    this.contractService.findAllContracts().subscribe(
      (response: Contract[]) => {
        this.contracts = response;
      }
    );
  }

  public findAllCustomers():void{
    this.customerService.findAllCustomer().subscribe(
      (response: Customer[]) => {
        this.customers = response;
      }
    );
  }

  public findAllVehicles():void{
    this.vehicleService.findAllVehicle().subscribe(
      (response: Vehicle[]) => {
        this.vehicles = response;
      }
    );
  }

  public saveContract(addForm: NgForm): void {
    var customer = this.customers.find(c => c.customerId === addForm.value.customerId);
    var vehicle = this.vehicles.find(c => c.vehicleId === addForm.value.vehicleId);
    this.contract.contractId = addForm.value.contractId;
    this.contract.contractNumber = addForm.value.contractNumber;
    this.contract.monthlyRate = addForm.value.monthlyRate;
    this.contract.customer = customer;
    this.contract.vehicle = vehicle;

    if(this.action == "add"){
      this.addContract(this.contract);
    }else{
      this.updateContract(this.contract);
    }
    addForm.reset();
  }

  public addContract(contract: Contract): void {
    this.contractService.saveContract(contract).subscribe(
      () => {
        this.findAllContracts();
      },(error: HttpErrorResponse) => {
        alert(error.message);
      }
    );

  }

  public updateContract(contract: Contract): void {
    this.contractService.updateContract(this.contract).subscribe(
      () => {
        this.findAllContracts();
        this.api.refreshCells();
      },(error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public deleteContract(): void {
    var selectedRows = this.api.getSelectedRows();
    if (selectedRows.length == 0) {
      alert("Please select a contract");
      return;
    }
    if (confirm("Are you sure you want to delete?")) {
      this.contractService.deleteContractById(selectedRows[0].contractId).subscribe(data => {
        this.findAllContracts();
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
      alert("Please select a contract");
      return;
    }
    this.contract = new Contract(
      selectedRow.contractId,
      selectedRow.contractNumber,
      selectedRow.monthlyRate,
      selectedRow.customer,
      selectedRow.vehicle,
    )
  }
}
