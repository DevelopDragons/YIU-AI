package devdragons.yiuServer.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class GraduationRequestDto {
    private Integer year;
    private List<MultipartFile> file;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
