package devdragons.yiuServer.repository;

import devdragons.yiuServer.domain.Graduation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GraduationRepository extends JpaRepository<Graduation, Integer> {
    Graduation findByYear(Integer year);
}
