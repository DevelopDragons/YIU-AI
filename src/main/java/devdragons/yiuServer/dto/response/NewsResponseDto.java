package devdragons.yiuServer.dto.response;

import devdragons.yiuServer.domain.Files;
import devdragons.yiuServer.domain.News;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsResponseDto {
    private Integer id;
    private String title;
    private String shorts;
    private String contents;
    private List<FileResponseDto> thumbnails;
    private List<FileResponseDto> gallery;
    private List<FileResponseDto> file;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    public static NewsResponseDto GetNewsDto(News news, List<Files> thumbnails, List<Files> gallery , List<Files> file) {
        List<FileResponseDto> thumbnailsDto = thumbnails.stream()
                .map(files -> new FileResponseDto(
                        files.getId(),
                        files.getType(),
                        files.getTypeId(),
                        files.getCategory(),
                        files.getOriginName(),
                        files.getSaveName(),
                        files.getSize(),
                        files.getCreatedAt()
                ))
                .collect(Collectors.toList());

        List<FileResponseDto> galleryDto = gallery.stream()
                .map(files -> new FileResponseDto(
                        files.getId(),
                        files.getType(),
                        files.getTypeId(),
                        files.getCategory(),
                        files.getOriginName(),
                        files.getSaveName(),
                        files.getSize(),
                        files.getCreatedAt()
                ))
                .collect(Collectors.toList());

        List<FileResponseDto> fileDto = file.stream()
                .map(files -> new FileResponseDto(
                        files.getId(),
                        files.getType(),
                        files.getTypeId(),
                        files.getCategory(),
                        files.getOriginName(),
                        files.getSaveName(),
                        files.getSize(),
                        files.getCreatedAt()
                ))
                .collect(Collectors.toList());

        return new NewsResponseDto(
                news.getId(),
                news.getTitle(),
                news.getShorts(),
                news.getContents(),
                thumbnailsDto,
                galleryDto,
                fileDto,
                news.getCreatedAt(),
                news.getUpdatedAt()
        );
    }
}
