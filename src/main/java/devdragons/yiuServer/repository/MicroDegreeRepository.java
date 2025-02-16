package devdragons.yiuServer.repository;

import devdragons.yiuServer.domain.MicroDegree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MicroDegreeRepository extends JpaRepository<MicroDegree, Integer> {
}
