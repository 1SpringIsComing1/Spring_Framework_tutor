package maksym.kruhovykh.app.repository;

import maksym.kruhovykh.app.repository.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface DriverRepository extends JpaRepository<Driver, Integer> {

}
