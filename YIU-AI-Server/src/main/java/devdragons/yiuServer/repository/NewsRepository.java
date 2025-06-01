package devdragons.yiuServer.repository;

import devdragons.yiuServer.domain.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {
    Optional<News> findByTitle(String title);

    Page<News> findByTitleContainingOrContentsContaining(String title, String contents, Pageable pageable);

    Page<News> findByTitleContaining(String title, Pageable pageable);

    Page<News> findByContentsContaining(String contents, Pageable pageable);
}
