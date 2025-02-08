package devdragons.yiuServer.repository;

import devdragons.yiuServer.domain.Council;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CouncilRepository extends JpaRepository<Council, Integer> {
    Optional<Council> findByName(String name);
}
