package devdragons.yiuServer.dto.response;

import devdragons.yiuServer.domain.MicroDegree;
import devdragons.yiuServer.domain.state.MicroDegreeCategory;
import devdragons.yiuServer.domain.state.RequiredCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MicroDegreeResponseDto {
    private String title;
    private String name;
    private String description;
    private RequiredCategory required;
    private MicroDegreeCategory category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static MicroDegreeResponseDto GetMicroDegreeDto(MicroDegree microDegree) {
        return new MicroDegreeResponseDto(
                microDegree.getTitle(),
                microDegree.getName(),
                microDegree.getDescription(),
                microDegree.getRequired(),
                microDegree.getCategory(),
                microDegree.getCreatedAt(),
                microDegree.getUpdatedAt()
        );
    }
}
