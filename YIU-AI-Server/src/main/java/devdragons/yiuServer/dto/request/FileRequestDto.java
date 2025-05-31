package devdragons.yiuServer.dto.request;

import devdragons.yiuServer.domain.state.FileType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class FileRequestDto {
    private FileType type;
    private Integer typeId;
    private String category;
    private String originName;
    private String saveName;
    private Long size;
    private LocalDateTime createAt;

    @Builder
    public FileRequestDto(String originName, String saveName, Long size) {
        this.originName = originName;
        this.saveName = saveName;
        this.size = size;
    }
}
