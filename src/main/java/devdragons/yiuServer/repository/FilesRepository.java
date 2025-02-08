package devdragons.yiuServer.repository;

import devdragons.yiuServer.domain.Files;
import devdragons.yiuServer.domain.state.FileType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilesRepository extends JpaRepository<Files, Integer> {
    List<Files> findAllByTypeAndTypeId(FileType type, Integer typeId);
    void deleteAllByTypeAndTypeId(FileType type, Integer typeId);
}
