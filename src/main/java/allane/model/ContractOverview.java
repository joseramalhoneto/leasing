package allane.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContractOverview {
    private long contractOverviewId;
    private int contractNumber;
    private String firstName;
    private String lastName;
    private String brand;
    private String model;
    private int year;
    private String vin;
    private double monthlyRate;
    private double price;

    public ContractOverview(int contractNumber, String firstName, String lastName, String brand, String model, int year, String vin, double monthlyRate, double price) {
        this.contractNumber = contractNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.vin = vin;
        this.monthlyRate = monthlyRate;
        this.price = price;
    }
}
