package devdragons.yiuServer.dto.response;

import devdragons.yiuServer.domain.Files;
import devdragons.yiuServer.domain.MOU;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MouResponseDto {
    private Integer id;
    private String name;
    private String description;
    private List<FileResponseDto> document;
    private List<FileResponseDto> image;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static MouResponseDto GetMouDto(MOU mou, List<Files> image, List<Files> document) {
        List<FileResponseDto> imageDtos = image.stream()
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

        List<FileResponseDto> documentDtos = document.stream()
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

        return new MouResponseDto(
                mou.getId(),
                mou.getName(),
                mou.getDescription(),
                imageDtos,
                documentDtos,
                mou.getCreatedAt(),
                mou.getUpdatedAt()
        );
    }
}
