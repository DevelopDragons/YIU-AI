package devdragons.yiuServer.controller;

import devdragons.yiuServer.domain.News;
import devdragons.yiuServer.dto.request.NewsRequestDto;
import devdragons.yiuServer.dto.response.NewsResponseDto;
import devdragons.yiuServer.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController extends CommonController<NewsResponseDto, NewsRequestDto> {
    private final NewsService newsService;

    @Override
    protected boolean createEntity(NewsRequestDto requestDto) throws Exception {
        return newsService.createNews(requestDto);
    }

    @Override
    protected boolean updateEntity(Integer id, NewsRequestDto requestDto) throws Exception {
        return newsService.updateNews(id, requestDto);
    }

    @Override
    protected boolean deleteEntity(Integer id) throws Exception {
        return newsService.deleteNews(id);
    }

    @Override
    protected List<NewsResponseDto> getEntities() throws Exception {
        return newsService.getNews();
    }

    @GetMapping("/search")
    public ResponseEntity<Page<News>> searchNews(@RequestParam String keyword, @PageableDefault(size = 10) Pageable pageable) {
        return new ResponseEntity<>(newsService.searchNews(keyword, pageable), HttpStatus.OK);
    }

    @GetMapping("/search-title")
    public ResponseEntity<Page<News>> searchNewsWithTitle(@RequestParam String title, @PageableDefault(size = 10) Pageable pageable) {
        return new ResponseEntity<>(newsService.searchNewsWithTitle(title, pageable), HttpStatus.OK);
    }

    @GetMapping("/search-contents")
    public ResponseEntity<Page<News>> searchNewsWithContents(@RequestParam String contents, Pageable pageable) {
        return new ResponseEntity<>(newsService.searchNewsWithContents(contents, pageable), HttpStatus.OK);
    }
}
