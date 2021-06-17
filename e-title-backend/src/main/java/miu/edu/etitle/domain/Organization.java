package miu.edu.etitle.domain;

import lombok.Getter;
import lombok.Setter;
import miu.edu.etitle.domain.address.Address;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String name;
    private String introduction;

    @OneToOne
    @JoinColumn
    private Address address;
}
