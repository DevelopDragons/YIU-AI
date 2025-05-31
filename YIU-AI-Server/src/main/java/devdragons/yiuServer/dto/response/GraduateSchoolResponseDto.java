package devdragons.yiuServer.dto.response;

import devdragons.yiuServer.domain.Files;
import devdragons.yiuServer.domain.GraduateSchool;
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
public class GraduateSchoolResponseDto {
    private Integer id;
    private String name;
    private String slogan;
    private List<FileResponseDto> image;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static GraduateSchoolResponseDto GetGraduateSchoolDto(GraduateSchool graduateSchool, List<Files> image) {
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

        return new GraduateSchoolResponseDto(
                graduateSchool.getId(),
                graduateSchool.getName(),
                graduateSchool.getSlogan(),
                imageDto,
                graduateSchool.getCreatedAt(),
                graduateSchool.getUpdatedAt()
        );
    }
}
