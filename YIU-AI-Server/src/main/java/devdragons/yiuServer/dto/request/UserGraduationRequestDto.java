package devdragons.yiuServer.dto.request;

import devdragons.yiuServer.domain.GraduationDetail;
import devdragons.yiuServer.domain.User;
import devdragons.yiuServer.domain.state.StatusCategory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class UserGraduationRequestDto {
    private Integer id;
    private User user;
    private GraduationDetail graduationDetail;
    private StatusCategory status;
    private String feedback;
    private String description;
    private List<MultipartFile> file;
}
