package devdragons.yiuServer.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CouncilRequestDto {
    private Integer id;
    private String name;
    private String link;
    private Integer year;
    private String slogan;
    private String description;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}
