package miu.edu.etitle.repository;

import miu.edu.etitle.domain.address.City;
import miu.edu.etitle.domain.address.State;
import org.springframework.data.repository.CrudRepository;

public interface CityRepository extends CrudRepository<City, Long> {
    City findByName(String stateName);
}
