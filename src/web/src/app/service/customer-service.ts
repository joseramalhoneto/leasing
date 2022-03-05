import { Customer } from './../model/customer';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http'
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable()
export class CustomerService {

  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  public findAllCustomer(): Observable<any>{
    return this.http.get(`${this.apiServerUrl}/customer/find/all`);
  }

  public findCustomerById(customerId: number): Observable<any>{
    return this.http.get(`${this.apiServerUrl}/customer/find/${customerId}`);
  }

  public saveCustomer(customer: Customer): Observable<any>{
    return this.http.post(`${this.apiServerUrl}/customer/save`, customer);
  }

  public updateCustomer(customer: Customer): Observable<any>{
    return this.http.put(`${this.apiServerUrl}/customer/update`, customer);
  }

  public deleteCustomerById(customerId: number){
    return this.http.delete(`${this.apiServerUrl}/customer/delete/${customerId}`);
  }

}
