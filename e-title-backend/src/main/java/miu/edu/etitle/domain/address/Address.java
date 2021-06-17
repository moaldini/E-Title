package miu.edu.etitle.domain.address;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String addressLine1;
    private String addressLine2;
    private String zipCode;
    private String county;

    @OneToOne
    @JoinColumn
    private City city;
    @OneToOne
    @JoinColumn
    private State state;

    public Address(String addressLine1, String addressLine2, String zipCode, String county, City city, State state) {
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.zipCode = zipCode;
        this.county = county;
        this.city = city;
        this.state = state;
    }
}