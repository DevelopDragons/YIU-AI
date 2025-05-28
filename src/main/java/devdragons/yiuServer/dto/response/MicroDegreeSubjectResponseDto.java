package devdragons.yiuServer.dto.response;

import devdragons.yiuServer.domain.MicroDegreeSubject;
import devdragons.yiuServer.domain.state.ClassCategory;
import devdragons.yiuServer.domain.state.CourseCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.security.auth.Subject;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MicroDegreeSubjectResponseDto {
    private String title;
    private String description;
    private String code;
    private CourseCategory course;
    private Integer credit;

    public static MicroDegreeSubjectResponseDto from(MicroDegreeSubject subject) {
        return MicroDegreeSubjectResponseDto.builder()
                .title(subject.getTitle())
                .description(subject.getDescription())
                .code(subject.getCode())
                .course(subject.getCourse())
                .credit(subject.getCredit())
                .build();
    }
}
