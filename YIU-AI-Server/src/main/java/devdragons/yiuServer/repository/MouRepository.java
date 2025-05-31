package devdragons.yiuServer.repository;

import devdragons.yiuServer.domain.MOU;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MouRepository extends JpaRepository<MOU, Integer> {
    Optional<MOU> findByName(String name);
}
