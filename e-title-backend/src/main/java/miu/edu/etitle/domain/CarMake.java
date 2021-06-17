package miu.edu.etitle.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import miu.edu.etitle.domain.address.Country;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
public class CarMake {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    private String code;
    private String name;
    private String avatarUrl;

    @ManyToOne
    @JoinColumn
    private Country country;
    private String introduction;
}
