package miu.edu.etitle.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import miu.edu.etitle.domain.address.Country;

import javax.persistence.*;

@Entity
@Setter
@Getter
@ToString
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String code;
    private String name;
    private String avatarUrl;
    @ManyToOne
    @JoinColumn
    private Country country;
    private String introduction;
    private ModelSizeType modelSizeType;
}
