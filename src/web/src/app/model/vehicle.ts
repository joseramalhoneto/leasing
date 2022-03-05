export class Vehicle{
  vehicleId?: number;
  brand?: string;
  model?: string;
  vin?: string;
  year?: number;
  price?: number;

  constructor(
    vehicleId?: number,
    brand?: string,
    model?: string,
    vin?: string,
    year?: number,
    price?: number
  ) {
      this.vehicleId = vehicleId;
      this.brand = brand;
      this.model = model;
      this.vin = vin;
      this.year = year;
      this.price = price;
    }
}


