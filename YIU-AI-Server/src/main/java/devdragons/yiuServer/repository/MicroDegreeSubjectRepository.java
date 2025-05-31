package devdragons.yiuServer.repository;

import devdragons.yiuServer.domain.MicroDegreeSubject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MicroDegreeSubjectRepository extends JpaRepository<MicroDegreeSubject, Integer> {
    Optional<MicroDegreeSubject> findByTitle(String title);
    List<MicroDegreeSubject> findByCategory(String category);
}
