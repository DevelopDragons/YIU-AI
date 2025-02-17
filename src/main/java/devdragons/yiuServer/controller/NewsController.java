package devdragons.yiuServer.controller;

import devdragons.yiuServer.dto.request.NewsRequestDto;
import devdragons.yiuServer.dto.response.NewsResponseDto;
import devdragons.yiuServer.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/news")
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
}
