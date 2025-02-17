package devdragons.yiuServer.repository;

import devdragons.yiuServer.domain.GraduateSchool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GraduateSchoolRepository extends JpaRepository<GraduateSchool, Integer> {
    Optional<GraduateSchool> findByName(String name);
}
