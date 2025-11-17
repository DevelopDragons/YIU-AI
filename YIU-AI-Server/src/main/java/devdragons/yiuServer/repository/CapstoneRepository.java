package devdragons.yiuServer.repository;

import devdragons.yiuServer.domain.Capstone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CapstoneRepository extends JpaRepository<Capstone, Integer> {
}
