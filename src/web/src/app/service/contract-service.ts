import { environment } from '../../environments/environment';
import { Contract } from '../model/contract';
import { HttpClient } from '@angular/common/http'
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable()
export class ContractService {

  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  public findAllContracts(): Observable<any>{
    return this.http.get(`${this.apiServerUrl}/contract/find/all`);
  }

  public findContractById(contractId: number): Observable<any>{
    return this.http.get(`${this.apiServerUrl}/contract/find/${contractId}`);
  }

  public saveContract(contract: Contract): Observable<any>{
    return this.http.post(`${this.apiServerUrl}/contract/save`, contract);
  }

  public updateContract(contract: Contract): Observable<any>{
    return this.http.put(`${this.apiServerUrl}/contract/update`, contract);
  }

  public deleteContractById(contractId: number){
    return this.http.delete(`${this.apiServerUrl}/contract/delete/${contractId}`);
  }

  public getContractOverview(): Observable<any>{
    return this.http.get(`${this.apiServerUrl}/contract/overview`);
  }

}
