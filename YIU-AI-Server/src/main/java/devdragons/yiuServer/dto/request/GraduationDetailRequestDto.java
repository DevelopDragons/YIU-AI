package devdragons.yiuServer.dto.request;

import devdragons.yiuServer.domain.state.GraduationCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GraduationDetailRequestDto {
    private Integer year;
    private String title;
    private String content;
    private GraduationCategory graduationCategory;
}
