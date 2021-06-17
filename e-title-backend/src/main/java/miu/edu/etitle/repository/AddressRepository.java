package miu.edu.etitle.repository;

import miu.edu.etitle.domain.address.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Long> {

}