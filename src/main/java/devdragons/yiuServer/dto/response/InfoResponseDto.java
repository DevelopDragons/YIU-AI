package devdragons.yiuServer.dto.response;

import devdragons.yiuServer.domain.Files;
import devdragons.yiuServer.domain.Info;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InfoResponseDto {
    private String name;
    private String engName;
    private String address;
    private String tel;
    private String mail;
    private String professor;
    private String greeting;
    private List<FileResponseDto> bossImage;
    private String title;
    private String contents;
    private List<FileResponseDto> image;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static InfoResponseDto GetInfoDto(Info info, List<Files> bossImage, List<Files> image) {
        List<FileResponseDto> bossImageDto = bossImage.stream()
                .map(files -> new FileResponseDto(
                        files.getId(),
                        files.getType(),
                        files.getTypeId(),
                        files.getCategory(),
                        files.getOriginName(),
                        files.getSaveName(),
                        files.getSize(),
                        files.getCreatedAt()
                ))
                .collect(Collectors.toList());

        List<FileResponseDto> imageDto = image.stream()
                .map(files -> new FileResponseDto(
                        files.getId(),
                        files.getType(),
                        files.getTypeId(),
                        files.getCategory(),
                        files.getOriginName(),
                        files.getSaveName(),
                        files.getSize(),
                        files.getCreatedAt()
                ))
                .collect(Collectors.toList());

        return new InfoResponseDto(
                info.getName(),
                info.getEngName(),
                info.getAddress(),
                info.getTel(),
                info.getMail(),
                info.getProfessor(),
                info.getGreeting(),
                bossImageDto,
                info.getTitle(),
                info.getContents(),
                imageDto,
                info.getCreatedAt(),
                info.getUpdatedAt()
        );
    }
}
