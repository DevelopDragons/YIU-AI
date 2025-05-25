package devdragons.yiuServer.dto.response;

import devdragons.yiuServer.domain.Files;
import devdragons.yiuServer.domain.Greeting;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GreetingResponseDto {
    private Integer id;
    private String name;
    private String greeting;
    private List<FileResponseDto> image;
    private List<FileResponseDto> autograph;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static GreetingResponseDto GetGreetingDto(Greeting greeting, List<Files> image, List<Files> autograph) {
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

        List<FileResponseDto> autographDto = autograph.stream()
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

        return new GreetingResponseDto(
                greeting.getId(),
                greeting.getName(),
                greeting.getGreetings(),
                imageDto,
                autographDto,
                greeting.getCreatedAt(),
                greeting.getUpdatedAt()
        );
    }
}
