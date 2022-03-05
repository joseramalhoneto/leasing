import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ContractComponent } from './contract/contract.component';
import { CustomerComponent } from './customer/customer.component';
import { VehicleComponent } from './vehicle/vehicle.component';
import { ContractOverviewComponent } from './contract-overview/contract-overview.component';

const routes: Routes = [
  { path: '', component: ContractOverviewComponent },
  { path: 'contract', component: ContractComponent },
  { path: 'customer', component: CustomerComponent },
  { path: 'vehicle', component: VehicleComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
