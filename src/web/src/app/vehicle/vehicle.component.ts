import { Vehicle } from './../model/vehicle';
import { VehicleService } from './../service/vehicle-service';
import { Component, OnInit } from '@angular/core';
import { ColDef, GridApi, ColumnApi } from 'ag-grid-community';
import { NgForm } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-vehicle',
  templateUrl: './vehicle.component.html',
  styleUrls: ['./vehicle.component.css']
})
export class VehicleComponent implements OnInit {

  public vehicles: Vehicle[] = [];
  public columnDefs: ColDef[] = [];
  private api: GridApi = new GridApi;
  private columnApi: ColumnApi = new ColumnApi;
  private action?: string;
  public vehicle: Vehicle = new Vehicle();

  public defaultColDef: ColDef = {
    editable: false,
    sortable: true,
    flex: 1,
    minWidth: 100,
    filter: true,
    resizable: true,
  };

  constructor(private vehicleService: VehicleService) {
    this.columnDefs = this.createColumnDefs();
  }

  ngOnInit(): void {
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
       headerName: "vehicleId",
       field: "vehicleId"
    },{
        headerName: 'Brand',
        field: 'brand'
    }, {
        headerName: 'Model',
        field: 'model'
    }, {
      headerName: 'Year',
      field: 'year'
    }, {
      headerName: 'VIN',
      field: 'vin',
        valueGetter:function(params: any){
          if(params.data.vin === "" || params.data.vin === null)
            return " - ";
          else
            return params.data.vin;
       }
    }, {
      headerName: 'Price',
      field: 'price'
    }
    ]
  }

  public findAllVehicles():void{
    this.vehicleService.findAllVehicle().subscribe(
      (response: Vehicle[]) => {
        this.vehicles = response;
      }
    );
  }

  public saveVehicle(addForm: NgForm): void {
    this.vehicle.vehicleId = addForm.value.vehicleId;
    this.vehicle.brand = addForm.value.brand;
    this.vehicle.model = addForm.value.model;
    this.vehicle.year = addForm.value.year;
    this.vehicle.vin = addForm.value.vin;
    this.vehicle.price = addForm.value.price;

    if(this.action == "add"){
      this.addVehicle(this.vehicle);
    }else{
      this.updateVehicle(this.vehicle);
    }
    addForm.reset();
  }

  public addVehicle(vehicle: Vehicle): void {
    this.vehicleService.saveVehicle(vehicle).subscribe(
      () => {
        this.findAllVehicles();
      },(error: HttpErrorResponse) => {
        alert(error.error);
      }
    );

  }

  public updateVehicle(vehicle: Vehicle): void {
    this.vehicleService.updateVehicle(this.vehicle).subscribe(
      () => {
        this.findAllVehicles();
        this.api.refreshCells();
      },(error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public deleteVehicle(): void {
    var selectedRows = this.api.getSelectedRows();
    if (selectedRows.length == 0) {
      alert("Please select a vehicle");
      return;
    }
    if (confirm("Are you sure you want to delete?")) {
      this.vehicleService.deleteVehicleById(selectedRows[0].vehicleId).subscribe(data => {
        this.findAllVehicles();
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
      alert("Please select a vehicle");
      return;
    }
    console.log(selectedRow);
    this.vehicle = new Vehicle(
      selectedRow.vehicleId,
      selectedRow.brand,
      selectedRow.model,
      selectedRow.vin,
      selectedRow.year,
      selectedRow.price
    )
  }
}
