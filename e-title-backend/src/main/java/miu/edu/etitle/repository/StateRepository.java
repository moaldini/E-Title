package miu.edu.etitle.repository;

import miu.edu.etitle.domain.address.State;
import org.springframework.data.repository.CrudRepository;

public interface StateRepository extends CrudRepository<State, Long> {
    State findByName(String stateName);
    State findByCodeIgnoreCase(String codeName);
}
