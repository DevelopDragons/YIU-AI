package devdragons.yiuServer.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class CouncilRequestDto {
    private Integer id;
    private String name;
    private String link;
    private Integer year;
    private String slogan;
    private String description;
    private List<MultipartFile> thumbnails;
    private List<MultipartFile> people;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}
