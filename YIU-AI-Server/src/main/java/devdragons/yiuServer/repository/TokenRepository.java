package devdragons.yiuServer.repository;

import devdragons.yiuServer.domain.Token;
import org.springframework.data.repository.CrudRepository;

public interface TokenRepository extends CrudRepository<Token, String> {
}
