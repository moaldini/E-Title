package miu.edu.etitle.repository;

import miu.edu.etitle.domain.Car;
import miu.edu.etitle.domain.CarOwner;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface CarRepository extends CrudRepository<Car, Long> {
    Optional<Car> findByVin(String vin);
    Optional<List<Car>> findByCarOwner(CarOwner carOwner);

//    @Query(value="select c from Car c WHERE " +
//            "c.vin LIKE CONCAT('%',:searchtext,'%') " +
//            "OR c.make.name LIKE CONCAT('%',:searchtext,'%') " +
//            "OR c.model.name LIKE CONCAT('%',:searchtext,'%') " +
//            "OR c.carOwner.ssn LIKE CONCAT('%',:searchtext,'%') " +
//            "OR c.year LIKE CONCAT('%',:searchtext,'%') " +
//            "OR c.currentRevision.condition LIKE CONCAT('%',:searchtext,'%') " +
//            "OR c.year LIKE CONCAT('%',:searchtext,'%') " +
//            "OR c.size LIKE CONCAT('%',:searchtext,'%') " +
//            "OR c.currentRevision.status LIKE CONCAT('%',:searchtext,'%') " +
//            "OR c.bodyType LIKE CONCAT('%',:searchtext,'%') " +
//            "OR c.fwd LIKE CONCAT('%',:searchtext,'%') " +
//            "OR c.currentRevision.status LIKE CONCAT('%',:searchtext,'%') " +
//            "OR c.currentRevision.state.name LIKE CONCAT('%',:searchtext,'%') " +
//            "OR c.status LIKE CONCAT('%',:searchtext,'%')" )
//    List<Car> findByAllColumns(@Param("searchtext") String searchtext);
    @Query(value="select c from Car c WHERE " + "c.vin LIKE CONCAT('%',:searchtext,'%')" )
    List<Car> findByAllColumns(@Param("searchtext") String searchtext);
}
