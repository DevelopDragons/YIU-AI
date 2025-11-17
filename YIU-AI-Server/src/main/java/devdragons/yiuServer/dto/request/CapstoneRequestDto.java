package devdragons.yiuServer.dto.request;

import devdragons.yiuServer.domain.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class CapstoneRequestDto {
    private String teamName;
    private List<MultipartFile> thumbnail;
    private String professor;
    private String topic;
    private String description;
    private String link;
    private List<User> users;
}
