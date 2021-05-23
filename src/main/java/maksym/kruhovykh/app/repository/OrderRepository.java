package maksym.kruhovykh.app.repository;

import maksym.kruhovykh.app.repository.entity.Client;
import maksym.kruhovykh.app.repository.entity.Order;
import maksym.kruhovykh.app.utils.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    boolean existsByStatusAndClient(Status status, Client client);
}
