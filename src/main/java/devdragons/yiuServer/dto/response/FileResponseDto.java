package devdragons.yiuServer.dto.response;

import devdragons.yiuServer.domain.state.FileType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileResponseDto {
    private Integer id;
    private FileType type;
    private Integer typeId;
    private String category;
    private String originName;
    private String saveName;
    private Long size;
    private LocalDateTime createAt;
}
