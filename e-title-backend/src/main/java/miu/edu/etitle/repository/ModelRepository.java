package miu.edu.etitle.repository;

import miu.edu.etitle.domain.Model;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ModelRepository extends CrudRepository<Model, Integer> {
    Optional<Model> findByName(String name);
}
