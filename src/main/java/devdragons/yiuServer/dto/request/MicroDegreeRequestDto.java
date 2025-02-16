package devdragons.yiuServer.dto.request;

import devdragons.yiuServer.domain.state.MicroDegreeCategory;
import devdragons.yiuServer.domain.state.RequiredCategory;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MicroDegreeRequestDto {
    private String title;
    private String name;
    private String description;
    private RequiredCategory required;
    private MicroDegreeCategory category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
