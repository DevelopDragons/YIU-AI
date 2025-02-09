package devdragons.yiuServer.service;

import devdragons.yiuServer.domain.News;
import devdragons.yiuServer.dto.request.NewsRequestDto;
import devdragons.yiuServer.dto.response.NewsResponseDto;
import devdragons.yiuServer.exception.CustomException;
import devdragons.yiuServer.exception.ErrorCode;
import devdragons.yiuServer.repository.NewsRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NewsServiceTest {
    @Autowired
    private NewsService newsService;

    @Autowired
    private NewsRepository newsRepository;

    @BeforeEach
    void setUp() {
        newsRepository.deleteAll();
    }

    @Test
    @DisplayName("뉴스 등록 성공 테스트")
    void 뉴스등록_성공() throws Exception {
        // given
        NewsRequestDto newsRequestDto = new NewsRequestDto();
        newsRequestDto.setTitle("title");
        newsRequestDto.setShorts("shorts");
        newsRequestDto.setContents("contents");

        // when
        newsService.createNews(newsRequestDto);

        // then
        Optional<News> news = newsRepository.findByTitle("title");
    }

    @Test
    @DisplayName("뉴스 등록 실패 테스트 - 데이터 부족")
    void 뉴스등록_실패_데이터부족() throws Exception {
        // given
        NewsRequestDto newsRequestDto = new NewsRequestDto();
        newsRequestDto.setContents("contents");

        // when & then
        CustomException customException = assertThrows(CustomException.class, () -> newsService.createNews(newsRequestDto));
        assertEquals(ErrorCode.INSUFFICIENT_DATA, customException.getErrorCode());
        assertEquals(400, customException.getErrorCode().getStatus());
    }

    @Test
    @DisplayName("뉴스 수정 성공 테스트")
    void 뉴스수정_성공() throws Exception {
        // given
        News news = News.builder()
                .title("title")
                .shorts("shorts")
                .contents("contents")
                .build();

        News savesNews = newsRepository.save(news);
        NewsRequestDto newsRequestDto = new NewsRequestDto();
        newsRequestDto.setTitle("제목");
        newsRequestDto.setShorts("단신");
        newsRequestDto.setContents("내용");

        // when
        newsService.updateNews(savesNews.getId(), newsRequestDto);

        // then
        Optional<News> news1 = newsRepository.findById(savesNews.getId());
        assertTrue(news1.get().getTitle().equals(newsRequestDto.getTitle()));
    }

    @Test
    @DisplayName("뉴스 수정 실패 테스트 - 데이터 부족")
    void 뉴스수정_실패_데이터부족() throws Exception {
        // given
        News news = News.builder()
                .title("title")
                .shorts("shorts")
                .contents("contents")
                .build();

        News savedNews = newsRepository.save(news);
        NewsRequestDto newsRequestDto = new NewsRequestDto();
        newsRequestDto.setContents("내용");

        // when & then
        CustomException customException = assertThrows(CustomException.class, () -> newsService.updateNews(savedNews.getId(), newsRequestDto));
        assertEquals(ErrorCode.INSUFFICIENT_DATA, customException.getErrorCode());
        assertEquals(400, customException.getErrorCode().getStatus());
    }

    @Test
    @DisplayName("뉴스 삭제 성공 테스트")
    void 뉴스삭제_성공() throws Exception {
        // given
        News news = News.builder()
                .title("title")
                .shorts("shorts")
                .contents("contents")
                .build();

        News savedNews = newsRepository.save(news);

        // when
        newsService.deleteNews(savedNews.getId());

        // then
        assertTrue(newsRepository.findById(savedNews.getId()).isEmpty());
    }

    @Test
    @DisplayName("뉴스 삭제 실패 테스트 - 해당 데이터 없음")
    void 뉴스삭제_실패_해당id미존재() throws Exception {
        // given
        int id = 1;

        // when & then
        CustomException customException = assertThrows(CustomException.class, () -> newsService.deleteNews(id));
        assertEquals(ErrorCode.NOT_EXIST_ID, customException.getErrorCode());
        assertEquals(404, customException.getErrorCode().getStatus());
    }

    @Test
    @DisplayName("뉴스 조회 성공 테스트")
    void 뉴스조회성공() throws Exception {
        // given
        NewsRequestDto newsRequestDto = new NewsRequestDto();
        newsRequestDto.setTitle("title");
        newsRequestDto.setShorts("shorts");
        newsRequestDto.setContents("contents");
        newsService.createNews(newsRequestDto);

        NewsRequestDto newsRequestDto2 = new NewsRequestDto();
        newsRequestDto2.setTitle("title");
        newsRequestDto2.setShorts("shorts");
        newsRequestDto2.setContents("contents");
        newsService.createNews(newsRequestDto2);

        // when
        List<NewsResponseDto> newsResponseDtoList = newsService.getNews();

        // then
        assertTrue(newsResponseDtoList.size() == 2);
    }
}