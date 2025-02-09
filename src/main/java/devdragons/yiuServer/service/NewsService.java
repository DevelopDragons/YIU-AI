package devdragons.yiuServer.service;

import devdragons.yiuServer.domain.Files;
import devdragons.yiuServer.domain.News;
import devdragons.yiuServer.domain.state.FileType;
import devdragons.yiuServer.dto.request.FileRequestDto;
import devdragons.yiuServer.dto.request.NewsRequestDto;
import devdragons.yiuServer.dto.response.NewsResponseDto;
import devdragons.yiuServer.exception.CustomException;
import devdragons.yiuServer.exception.ErrorCode;
import devdragons.yiuServer.repository.FilesRepository;
import devdragons.yiuServer.repository.NewsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class NewsService {
    private final NewsRepository newsRepository;
    private final FilesRepository filesRepository;
    private final FileService fileService;

    /*
     * @description 뉴스 등록
     * @author 김예서
     * @param title, shorts, contents, thumbnail, gallery, file
     * @return boolean
     * */
    public Boolean createNews(NewsRequestDto requestDto) throws Exception {
        Predicate<Object> isNullOrEmpty = field ->
                field == null || (field instanceof String && ((String) field).isEmpty());

        List<Object> requiredFields = Arrays.asList(
                requestDto.getTitle(), requestDto.getShorts(), requestDto.getContents()
        );

        if(requiredFields.stream().anyMatch(isNullOrEmpty)) {
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }

        try {
            News news = News.builder()
                    .title(requestDto.getTitle())
                    .shorts(requestDto.getShorts())
                    .contents(requestDto.getContents())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            News savedNews = newsRepository.save(news);

            if(requestDto.getThumbnail() != null || requestDto.getGallery() != null || requestDto.getFile() != null) {
                List<FileRequestDto> thumbnails = fileService.uploadFiles(requestDto.getThumbnail());
                List<FileRequestDto> gallery = fileService.uploadFiles(requestDto.getGallery());
                List<FileRequestDto> file = fileService.uploadFiles(requestDto.getFile());

                fileService.saveFiles(FileType.NEWS, savedNews.getId(), "thumbnail", thumbnails);
                fileService.saveFiles(FileType.NEWS, savedNews.getId(), "gallery", gallery);
                fileService.saveFiles(FileType.NEWS, savedNews.getId(), "file", file);
            }
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * @description 뉴스 수정
     * @author 김예서
     * @param id, title, shorts, contents, thumbnail, gallery, file
     * @return boolean
     * */
    public Boolean updateNews(Integer id, NewsRequestDto requestDto) throws Exception {
        News news = newsRepository.findById(id).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_EXIST_ID));

        Predicate<Object> isNullOrEmpty = field ->
                field == null || (field instanceof String && ((String) field).isEmpty());

        List<Object> requiredFields = Arrays.asList(
                requestDto.getTitle(), requestDto.getShorts(), requestDto.getContents()
        );

        if(requiredFields.stream().anyMatch(isNullOrEmpty)) {
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }

        try {
            news.setTitle(requestDto.getTitle());
            news.setShorts(requestDto.getShorts());
            news.setContents(requestDto.getContents());
            news.setUpdatedAt(LocalDateTime.now());

            if(requestDto.getThumbnail() != null || requestDto.getGallery() != null || requestDto.getFile() != null) {
                List<Files> deleteFiles = filesRepository.findAllByTypeAndTypeId(FileType.NEWS, id);

                filesRepository.deleteAllByTypeAndTypeId(FileType.NEWS, id);

                List<FileRequestDto> thumbnails = fileService.uploadFiles(requestDto.getThumbnail());
                List<FileRequestDto> gallery = fileService.uploadFiles(requestDto.getGallery());
                List<FileRequestDto> file = fileService.uploadFiles(requestDto.getFile());

                fileService.saveFiles(FileType.NEWS, id, "thumbnail", thumbnails);
                fileService.saveFiles(FileType.NEWS, id, "gallery", gallery);
                fileService.saveFiles(FileType.NEWS, id, "file", file);
            }
            newsRepository.save(news);
            return true;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * @description 뉴스 삭제
     * @author 김예서
     * @param id
     * @return boolean
     * */
    public Boolean deleteNews(Integer id) throws Exception {
        News news = newsRepository.findById(id).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_EXIST_ID));

        try {
            List<Files> deleteFiles = filesRepository.findAllByTypeAndTypeId(FileType.NEWS, id);
            fileService.deleteFiles(deleteFiles);

            filesRepository.deleteAllByTypeAndTypeId(FileType.NEWS, id);

            newsRepository.delete(news);
            return true;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * @description 뉴스 조회
     * @author 김예서
     * @return
     * */
    public List<NewsResponseDto> getNews() throws Exception {
        List<News> news = newsRepository.findAll();
        List<Files> thumbnails = filesRepository.findAllByTypeAndCategory(FileType.NEWS, "thumbnail");
        List<Files> gallery = filesRepository.findAllByTypeAndCategory(FileType.NEWS, "gallery");
        List<Files> file = filesRepository.findAllByTypeAndCategory(FileType.NEWS, "file");

        List<NewsResponseDto> getListDto = new ArrayList<>();
        for(News news1: news) {
            List<Files> filteredThumbnails = thumbnails.stream()
                    .filter(files -> files.getTypeId().equals(news1.getId()))
                    .collect(Collectors.toList());
            List<Files> filteredGallery = gallery.stream()
                    .filter(files -> files.getTypeId().equals(news1.getId()))
                    .collect(Collectors.toList());
            List<Files> filteredFile = file.stream()
                    .filter(files -> files.getTypeId().equals(news1.getId()))
                    .collect(Collectors.toList());
            getListDto.add(NewsResponseDto.GetNewsDto(news1, filteredThumbnails, filteredGallery, filteredFile));
        }
        return getListDto;
    }
}
