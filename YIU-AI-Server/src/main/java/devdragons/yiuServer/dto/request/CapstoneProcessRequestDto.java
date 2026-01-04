package devdragons.yiuServer.dto.request;

import devdragons.yiuServer.domain.Capstone;
import devdragons.yiuServer.domain.state.CapstoneProcessCategory;
import devdragons.yiuServer.domain.state.StatusCategory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class CapstoneProcessRequestDto {
    private Capstone capstone;
    private CapstoneProcessCategory category;
    private StatusCategory status;
    private String description;
    private String contents;
    private String link;
    private String feedback;
    private List<MultipartFile> file;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
