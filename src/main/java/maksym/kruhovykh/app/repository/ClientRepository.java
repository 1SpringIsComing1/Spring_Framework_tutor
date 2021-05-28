package maksym.kruhovykh.app.repository;

import maksym.kruhovykh.app.repository.entity.Client;
import maksym.kruhovykh.app.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    Optional<Client> findByUser(User user);

    Optional<Client> findClientByUserEmail(String email);

//
//    @Query(value = "select * from Client cl left join user u on u.id = cl.user_id where u.email = ?1",nativeQuery = true)
//    Optional<Client> findClientByUserEmail(String email);
}
