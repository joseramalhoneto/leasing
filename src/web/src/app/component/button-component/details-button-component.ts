import { Component } from '@angular/core';
import { ICellRendererAngularComp } from 'ag-grid-angular';
import { ICellRendererParams } from 'ag-grid-community';

@Component({
  selector: 'app-button-component',
  template: `<button (click)="showDialog()" class="btn btn-light tooltips" ><i class="fa fa-search"></i></button>`
})
export class DetailButtonComponent implements ICellRendererAngularComp {

  public cellValue!: string;

  constructor() { }

  refresh(params: ICellRendererParams): boolean {
    this.cellValue = this.getValueToDisplay(params);
    return true;
  }
  agInit(params: ICellRendererParams): void {
    this.cellValue = this.getValueToDisplay(params);
  }

  getValueToDisplay(params: ICellRendererParams) {
    return params.valueFormatted ? params.valueFormatted : params.value;
  }

  showDialog() {
    alert("The contract was saved successfully");
  }

}
