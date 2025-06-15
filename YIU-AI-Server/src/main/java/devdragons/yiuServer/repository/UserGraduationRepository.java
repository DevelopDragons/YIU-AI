package devdragons.yiuServer.repository;

import devdragons.yiuServer.domain.GraduationDetail;
import devdragons.yiuServer.domain.User;
import devdragons.yiuServer.domain.UserGraduation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserGraduationRepository extends JpaRepository<UserGraduation, Integer> {
    List<UserGraduation> findByGraduationDetail(GraduationDetail graduationDetail);

    List<UserGraduation> findAllByUser(User user);
}
