package devdragons.yiuServer.controller;

import devdragons.yiuServer.dto.request.NewsRequestDto;
import devdragons.yiuServer.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;

@Controller
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {
    private final NewsService newsService;

    // 뉴스 등록
    @PostMapping(value = "/admin", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Boolean> createNews(NewsRequestDto requestDto) throws Exception {
        return new ResponseEntity<>(newsService.createNews(requestDto), HttpStatus.OK);
    }

    // 뉴스 수정
    @PutMapping(value = "/admin", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Boolean> updateNews(@RequestParam(value = "id") Integer id, NewsRequestDto requestDto) throws Exception {
        return new ResponseEntity<>(newsService.updateNews(id, requestDto), HttpStatus.OK);
    }

    // 뉴스 삭제
    @DeleteMapping(value = "/admin")
    public ResponseEntity<Boolean> deleteNews(@RequestParam(value = "id") Integer id) throws Exception {
        return new ResponseEntity<>(newsService.deleteNews(id), HttpStatus.OK);
    }

    // 뉴스 조회
    @GetMapping
    public ResponseEntity<Object> getNews() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return new ResponseEntity<>(newsService.getNews(), headers, HttpStatus.OK);
    }
}
