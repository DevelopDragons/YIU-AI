package devdragons.yiuServer.dto.response;

import devdragons.yiuServer.domain.Council;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouncilResponseDto {
    private Integer id;
    private String name;
    private String link;
    private Integer year;
    private String slogan;
    private String description;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    public static CouncilResponseDto GetCouncilDto(Council council) {
        return new CouncilResponseDto(
                council.getId(),
                council.getName(),
                council.getLink(),
                council.getYear(),
                council.getSlogan(),
                council.getDescription(),
                council.getCreatedAt(),
                council.getUpdatedAt()
        );
    }
}
