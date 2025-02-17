package devdragons.yiuServer.repository;

import devdragons.yiuServer.domain.MicroDegree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MicroDegreeRepository extends JpaRepository<MicroDegree, Integer> {
    Optional<MicroDegree> findByName(String name);
}
