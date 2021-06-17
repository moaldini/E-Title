package miu.edu.etitle.repository;

import miu.edu.etitle.domain.CarMake;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CarMakeRepository extends CrudRepository<CarMake, Integer> {
    Optional<CarMake> findByName(String name);
}
