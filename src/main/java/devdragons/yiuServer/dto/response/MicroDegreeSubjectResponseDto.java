package devdragons.yiuServer.dto.response;

import devdragons.yiuServer.domain.MicroDegreeSubject;
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
    private Integer subjectNum;
    private String classes;
    private Integer credit;

    public static MicroDegreeSubjectResponseDto from(MicroDegreeSubject subject) {
        return MicroDegreeSubjectResponseDto.builder()
                .title(subject.getTitle())
                .description(subject.getDescription())
                .subjectNum(subject.getSubjectNum())
                .classes(subject.getClasses())
                .credit(subject.getCredit())
                .build();
    }
}
