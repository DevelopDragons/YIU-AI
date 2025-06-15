package devdragons.yiuServer.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GraduationDetailRequestDto {
    private Integer year;
    private String title;
    private String content;
}
