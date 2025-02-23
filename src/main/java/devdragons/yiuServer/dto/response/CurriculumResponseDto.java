package devdragons.yiuServer.dto.response;

import devdragons.yiuServer.domain.Curriculum;
import devdragons.yiuServer.domain.state.ClassCategory;
import devdragons.yiuServer.domain.state.CourseCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurriculumResponseDto {
    private Integer id;
    private String title;
    private CourseCategory course;
    private Integer grade;
    private Integer term;
    private Integer credit;
    private String description;
    private ClassCategory classes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CurriculumResponseDto GetCurriculumDto(Curriculum curriculum) {
        return new CurriculumResponseDto(
                curriculum.getId(),
                curriculum.getTitle(),
                curriculum.getCourse(),
                curriculum.getGrade(),
                curriculum.getTerm(),
                curriculum.getCredit(),
                curriculum.getDescription(),
                curriculum.getClasses(),
                curriculum.getCreatedAt(),
                curriculum.getUpdatedAt()
        );
    }
}
