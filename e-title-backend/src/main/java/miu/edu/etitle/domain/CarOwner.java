package miu.edu.etitle.domain;

import lombok.Getter;
import lombok.Setter;
import miu.edu.etitle.domain.address.Address;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"SSN"})})
public class CarOwner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String ssn;
    private String firstName;
    private String lastName;

    @OneToOne
    @JoinColumn
    private Address address;

    @Override
    public String toString() {
        return "CarOwner{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
