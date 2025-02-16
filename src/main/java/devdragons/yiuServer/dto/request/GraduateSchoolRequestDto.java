package devdragons.yiuServer.dto.request;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class GraduateSchoolRequestDto {
    private String name;
    private String slogan;
    private List<MultipartFile> image;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
