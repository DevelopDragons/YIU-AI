package devdragons.yiuServer.repository;

import devdragons.yiuServer.domain.Info;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InfoRepository extends JpaRepository<Info, Integer> {
    Optional<Info> findByName(String name);
}
