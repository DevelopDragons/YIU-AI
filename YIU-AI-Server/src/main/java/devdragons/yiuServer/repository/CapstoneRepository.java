package devdragons.yiuServer.repository;

import devdragons.yiuServer.domain.Capstone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CapstoneRepository extends JpaRepository<Capstone, Integer> {
    List<Capstone> findAllByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}
