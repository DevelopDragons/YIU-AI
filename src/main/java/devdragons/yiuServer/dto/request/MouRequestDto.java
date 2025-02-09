package devdragons.yiuServer.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class MouRequestDto {
    private String name;
    private List<MultipartFile> image;
}
