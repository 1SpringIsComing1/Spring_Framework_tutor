package maksym.kruhovykh.app.repository;

import maksym.kruhovykh.app.repository.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {
    Optional<Location> findByLocationName(String locationName);
}
