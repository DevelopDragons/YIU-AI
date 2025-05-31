package devdragons.yiuServer.dto.request;

import devdragons.yiuServer.domain.state.MemberRoleCategory;
import devdragons.yiuServer.domain.state.ProfessorTypeCategory;
import devdragons.yiuServer.domain.state.RequiredCategory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class MemberRequestDto {
    private String name;
    private List<MultipartFile> image;
    private String mail;
    private String tel;
    private String labName;
    private List<MultipartFile> labImage;
    private String labLink;
    private String labCategory;
    private ProfessorTypeCategory type;
    private MemberRoleCategory role;
    private String description;
    private RequiredCategory required;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
