package miu.edu.etitle.repository;

import miu.edu.etitle.domain.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByName(String roleName);
}
