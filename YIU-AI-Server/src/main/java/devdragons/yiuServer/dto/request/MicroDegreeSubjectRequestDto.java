package devdragons.yiuServer.dto.request;

import devdragons.yiuServer.domain.state.CourseCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MicroDegreeSubjectRequestDto {
    private String title;
    private String description;
    private String category;
    private String code;
    private CourseCategory course;
    private Integer credit;
}
