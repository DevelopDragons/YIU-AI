package devdragons.yiuServer.dto.request;

import devdragons.yiuServer.domain.state.ClassCategory;
import devdragons.yiuServer.domain.state.CourseCategory;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CurriculumRequestDto {
    private String title;
    private CourseCategory course;
    private Integer grade;
    private Integer term;
    private Integer credit;
    private String description;
    private ClassCategory classes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
