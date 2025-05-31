package devdragons.yiuServer.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class GreetingRequestDto {
    private String name;
    private String greetings;
    private List<MultipartFile> image;
    private List<MultipartFile> autograph;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
