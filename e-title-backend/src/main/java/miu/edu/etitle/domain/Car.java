package miu.edu.etitle.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@ToString
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CAR_ID")
    Long id;

    @OneToOne
    @JoinColumn
    private CarMake make;

    @OneToOne
    @JoinColumn
    private Model model;
    private String year;

    @Column(unique = true)
    private String vin;
    private String color;
    private ModelFuelType modelFuelType;
    private String cylinders;
    private String transmission;
    private String size;
    private String fwd;
    private String bodyType;
    private String status; // PENDING_ORG_APPROVAL, ORG_APPROVED, WAITING_FOR_SALE, BUYER_OFFERED, SELLER_APPROVED

    @ManyToOne
    CarOwner carOwner;

    @OneToOne
    @JoinColumn
    @JsonIgnore
    CarTitleRevision currentRevision;

    @OneToMany(mappedBy = "car")
    @JsonIgnore
    List<CarTitleRevision> carTitleList; // history of a car: changing car owner (car's title), price, miles...
}
