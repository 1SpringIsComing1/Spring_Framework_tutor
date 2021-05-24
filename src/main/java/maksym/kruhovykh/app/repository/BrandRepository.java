package maksym.kruhovykh.app.repository;

import maksym.kruhovykh.app.repository.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {
    Optional<Brand> findByNameContainingIgnoreCase(String name);
}
