package devdragons.yiuServer.dto.request;

import devdragons.yiuServer.domain.MicroDegree;
import devdragons.yiuServer.domain.state.MicroDegreeCategory;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MicroDegreeSubjectRequestDto {
    private String title;
    private String description;
    private String category;
    private Integer subjectNum;
    private String classes;
    private Integer credit;
}
