import { Vehicle } from './../model/vehicle';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http'
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable()
export class VehicleService {

  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  public findAllVehicle(): Observable<any>{
    return this.http.get(`${this.apiServerUrl}/vehicle/find/all`);
  }

  public findVehicleById(vehicleId: number): Observable<any>{
    return this.http.get(`${this.apiServerUrl}/vehicle/find/${vehicleId}`);
  }

  public saveVehicle(vehicle: Vehicle): Observable<any>{
    return this.http.post(`${this.apiServerUrl}/vehicle/save`, vehicle);
  }

  public updateVehicle(vehicle: Vehicle): Observable<any>{
    return this.http.put(`${this.apiServerUrl}/vehicle/update`, vehicle);
  }

  public deleteVehicleById(vehicleId: number){
    return this.http.delete(`${this.apiServerUrl}/vehicle/delete/${vehicleId}`);
  }

}
