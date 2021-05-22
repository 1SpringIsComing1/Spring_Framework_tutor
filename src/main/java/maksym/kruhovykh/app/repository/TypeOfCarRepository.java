package maksym.kruhovykh.app.repository;

import maksym.kruhovykh.app.repository.entity.TypeOfCar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeOfCarRepository extends JpaRepository<TypeOfCar, Integer> {
}
