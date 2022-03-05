import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AgGridModule } from 'ag-grid-angular';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ContractComponent } from './contract/contract.component';
import { CustomerComponent } from './customer/customer.component';
import { VehicleComponent } from './vehicle/vehicle.component';
import { HttpClientModule } from '@angular/common/http';
import { ContractService } from './service/contract-service';
import { CustomerService } from './service/customer-service';
import { VehicleService } from './service/vehicle-service';
import { NavBarComponent } from './nav-bar/nav-bar.component';
import { FormsModule } from '@angular/forms';
import { ToastrModule } from 'ngx-toastr';
import { ToastrService } from 'ngx-toastr';
import { DetailButtonComponent } from './component/button-component/details-button-component';
import { ContractOverviewComponent } from './contract-overview/contract-overview.component';

@NgModule({
  declarations: [
    AppComponent,
    ContractComponent,
    CustomerComponent,
    VehicleComponent,
    NavBarComponent,
    DetailButtonComponent,
    ContractOverviewComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ToastrModule.forRoot({
      positionClass :'toast-bottom-right'
    }),
    AgGridModule.withComponents([]),
  ],
  providers: [
    ContractService,
    CustomerService,
    VehicleService,
    ToastrService,
  ],
  exports: [AppRoutingModule],
  bootstrap: [AppComponent]
})
export class AppModule { }
