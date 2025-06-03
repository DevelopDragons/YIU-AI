package devdragons.yiuServer.repository;

import devdragons.yiuServer.domain.User;
import devdragons.yiuServer.domain.state.UserRoleCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByName(String name);

    Optional<User> findById(String id);

    List<User> findAllByRole(UserRoleCategory role);

    List<User> findAllByRoleAndGrade(UserRoleCategory role, int grade);
}
