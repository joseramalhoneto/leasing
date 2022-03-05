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
@Table(name = "contract")
public class Contract {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    @Column(name = "contract_id")
    private long contractId;
    private int contractNumber;
    private double monthlyRate;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Vehicle vehicle;
}
