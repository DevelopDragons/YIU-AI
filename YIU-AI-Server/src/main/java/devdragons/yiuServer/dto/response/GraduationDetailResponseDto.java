package devdragons.yiuServer.dto.response;

import devdragons.yiuServer.domain.GraduationDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GraduationDetailResponseDto {
    private Integer id;
    private Integer year;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static GraduationDetailResponseDto GetGraduationDetailDto(GraduationDetail graduationDetail) {
        return new GraduationDetailResponseDto(
                graduationDetail.getId(),
                graduationDetail.getYear(),
                graduationDetail.getTitle(),
                graduationDetail.getContent(),
                graduationDetail.getCreatedAt(),
                graduationDetail.getUpdatedAt()
        );
    }
}
