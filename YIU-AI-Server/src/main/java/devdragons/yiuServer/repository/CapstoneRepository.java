package devdragons.yiuServer.repository;

import devdragons.yiuServer.domain.Capstone;
import devdragons.yiuServer.domain.Council;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CapstoneRepository extends JpaRepository<Capstone, Integer> {
}
