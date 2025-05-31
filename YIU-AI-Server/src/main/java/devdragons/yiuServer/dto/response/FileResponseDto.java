package devdragons.yiuServer.dto.response;

import devdragons.yiuServer.domain.state.FileType;
import lombok.Getter;

import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class FileResponseDto {
    private Integer id;
    private FileType type;
    private Integer typeId;
    private String category;
    private String originName;
    private String saveName;
    private Long size;
    private LocalDateTime createdAt;

    public FileResponseDto(Integer id, FileType type, Integer typeId, String category, String originName, String saveName, Long size, LocalDateTime createdAt) {
        String dataPath = createdAt.format(DateTimeFormatter.ofPattern("yyMMdd"));
        String uploadPath = Paths.get("/Users", "yeseokim", "dev", "upload-files", dataPath).toString();
        this.id = id;
        this.type = type;
        this.typeId = typeId;
        this.category = category;
        this.originName = originName;
        this.saveName = saveName;
        this.size = size;
        this.createdAt = createdAt;
    }
}
