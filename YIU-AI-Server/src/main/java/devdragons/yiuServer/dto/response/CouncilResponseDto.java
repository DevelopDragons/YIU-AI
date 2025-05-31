package devdragons.yiuServer.dto.response;

import devdragons.yiuServer.domain.Council;
import devdragons.yiuServer.domain.Files;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouncilResponseDto {
    private Integer id;
    private String name;
    private String link;
    private Integer year;
    private String slogan;
    private String description;
    private List<FileResponseDto> thumbnails;
    private List<FileResponseDto> people;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    public static CouncilResponseDto GetCouncilDto(Council council, List<Files> thumbnails, List<Files> people) {
        List<FileResponseDto> thumbnailsDto = thumbnails.stream()
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

        List<FileResponseDto> peopleDto = people.stream()
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

        return new CouncilResponseDto(
                council.getId(),
                council.getName(),
                council.getLink(),
                council.getYear(),
                council.getSlogan(),
                council.getDescription(),
                thumbnailsDto,
                peopleDto,
                council.getCreatedAt(),
                council.getUpdatedAt()
        );
    }
}
