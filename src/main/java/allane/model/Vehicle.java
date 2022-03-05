package allane.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Vehicle {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "vehicle_id")
    private long vehicleId;
    private String brand;
    private String model;
    private String vin;
    private int year;
    private double price;
}
