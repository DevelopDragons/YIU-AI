package devdragons.yiuServer.dto.request;

import devdragons.yiuServer.domain.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class CapstoneRequestDto {
    private Integer id;
    private String teamName;
    private String professor;
    private String topic;
    private String description;
    private String link;
    private List<MultipartFile> thumbnail;
    private List<User> user;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
