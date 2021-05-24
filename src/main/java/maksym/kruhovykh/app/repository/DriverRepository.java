package maksym.kruhovykh.app.repository;

import maksym.kruhovykh.app.repository.entity.Driver;
import maksym.kruhovykh.app.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface DriverRepository extends JpaRepository<Driver, Integer> {
    Optional<Driver> findByUser(User user);
}
