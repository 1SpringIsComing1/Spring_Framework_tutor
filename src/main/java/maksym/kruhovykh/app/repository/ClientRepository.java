package maksym.kruhovykh.app.repository;

import maksym.kruhovykh.app.repository.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ClientRepository extends JpaRepository<Client,Integer> {
}
