package devdragons.yiuServer.dto.response;

import devdragons.yiuServer.domain.Files;
import devdragons.yiuServer.domain.Graduation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GraduationResponseDto {
    private Integer year;
    private List<FileResponseDto> file;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static GraduationResponseDto GetGrauduationDto(Graduation graduation, List<Files> file) {
        List<FileResponseDto> fileDto = file.stream()
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

        return new GraduationResponseDto(
                graduation.getYear(),
                fileDto,
                graduation.getCreatedAt(),
                graduation.getUpdatedAt()
        );
    }
}
