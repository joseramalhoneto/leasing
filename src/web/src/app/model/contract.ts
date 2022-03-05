import { Customer } from './customer';
import { Vehicle } from './vehicle';

export class Contract {
  contractId?: number;
  contractNumber?: number;
  monthlyRate?: number;
  customer?: Customer;
  vehicle?: Vehicle;

  constructor(
      contractId?: number,
      contractNumber?: number,
      monthlyRate?: number,
      customer?: Customer,
      vehicle?: Vehicle
    ) {
        this.contractId = contractId;
        this.contractNumber = contractNumber;
        this.monthlyRate = monthlyRate;
        this.customer = customer;
        this.vehicle = vehicle;
      }

}
