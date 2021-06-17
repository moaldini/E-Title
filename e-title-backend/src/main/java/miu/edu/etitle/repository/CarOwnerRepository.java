package miu.edu.etitle.repository;

import miu.edu.etitle.domain.CarOwner;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CarOwnerRepository extends CrudRepository<CarOwner, Long> {
    Optional<CarOwner> findBySsn(String ssn);
}
