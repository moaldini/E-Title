package miu.edu.etitle.domain.address;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class City {
    @Id
    private String name;
    private String stateCode;

    public City(String cityName) {
        this.name = cityName;
    }
}
