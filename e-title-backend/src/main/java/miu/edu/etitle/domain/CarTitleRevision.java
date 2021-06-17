package miu.edu.etitle.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import miu.edu.etitle.domain.CarOwner;
import miu.edu.etitle.domain.address.State;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

// ======================== STORE ALL REVISION OF CAR TITLES =========================
// ======================== INFORMATION NEED TO STORED:
//========= Price, miles, owners
@Entity
@Setter
@Getter
public class CarTitleRevision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "CAR_ID")
    @JsonIgnore
    Car car;

    String condition;
    private String price;
    private String miles;

    @OneToOne
    CarOwner owner;

    private String status;

    LocalDate createdDate;

    @ManyToOne
    @JoinColumn
    State state;

    @Override
    public String toString() {
        return "CarTitleRevision{" +
                "condition='" + condition + '\'' +
                ", price=" + price +
                ", miles=" + miles +
                ", createdDate=" + createdDate +
                ", state=" + state +
                '}';
    }
}
