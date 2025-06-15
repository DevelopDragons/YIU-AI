package devdragons.yiuServer.repository;

import devdragons.yiuServer.domain.GraduationDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GraduationDetailRepository extends JpaRepository<GraduationDetail, Integer> {
    List<GraduationDetail> findAllByYear(Integer year);
}
