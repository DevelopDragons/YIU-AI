package devdragons.yiuServer.dto.response;

import devdragons.yiuServer.domain.GraduationDetail;
import devdragons.yiuServer.domain.User;
import devdragons.yiuServer.domain.UserGraduation;
import devdragons.yiuServer.domain.state.StatusCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserGraduationResponseDto {
    private Integer id;
    private String user;
    private GraduationDetail graduationDetail;
    private StatusCategory status;
    private String feedback;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static UserGraduationResponseDto GetUserGraduationDto(UserGraduation userGraduation) {
        return new UserGraduationResponseDto(
                userGraduation.getId(),
                userGraduation.getUser().getId(),
                userGraduation.getGraduationDetail(),
                userGraduation.getStatus(),
                userGraduation.getFeedback(),
                userGraduation.getDescription(),
                userGraduation.getCreatedAt(),
                userGraduation.getUpdatedAt()
        );
    }
}
