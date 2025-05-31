package devdragons.yiuServer.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class NewsRequestDto {
    private String title;
    private String shorts;
    private String contents;
    List<MultipartFile> thumbnail;
    List<MultipartFile> gallery;
    List<MultipartFile> file;
}
