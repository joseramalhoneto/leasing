export class Customer{
  customerId?: number;
  firstName?: string;
  lastName?: string;
  birthDate?: Date;

  constructor(
    customerId?: number,
    firstName?: string,
    lastName?: string,
    birthDate?: Date
  ) {
      this.customerId = customerId;
      this.firstName = firstName;
      this.lastName = lastName;
      this.birthDate = birthDate;
    }
}
