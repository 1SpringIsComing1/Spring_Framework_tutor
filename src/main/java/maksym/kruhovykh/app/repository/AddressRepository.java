package maksym.kruhovykh.app.repository;

import maksym.kruhovykh.app.repository.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
