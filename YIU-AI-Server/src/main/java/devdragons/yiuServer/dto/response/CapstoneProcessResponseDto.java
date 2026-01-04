package devdragons.yiuServer.dto.response;

import devdragons.yiuServer.domain.CapstoneProcess;
import devdragons.yiuServer.domain.Files;
import devdragons.yiuServer.domain.state.CapstoneProcessCategory;
import devdragons.yiuServer.domain.state.StatusCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CapstoneProcessResponseDto {
    private Integer id;
    private Integer capstoneId;
    private String teamName;
    private CapstoneProcessCategory category;
    private StatusCategory status;
    private String description;
    private String contents;
    private String link;
    private String feedback;
    private List<FileResponseDto> file;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CapstoneProcessResponseDto GetCapstoneProcessResponseDto(CapstoneProcess capstoneProcess, List<Files> files) {
        List<FileResponseDto> filesDto = files.stream()
                .map(file -> new FileResponseDto(
                        file.getId(),
                        file.getType(),
                        file.getTypeId(),
                        file.getCategory(),
                        file.getOriginName(),
                        file.getSaveName(),
                        file.getSize(),
                        file.getCreatedAt()
                ))
                .collect(Collectors.toList());

        return new CapstoneProcessResponseDto(
                capstoneProcess.getId(),
                capstoneProcess.getCapstone().getId(),
                capstoneProcess.getCapstone().getTeamName(),
                capstoneProcess.getCategory(),
                capstoneProcess.getStatus(),
                capstoneProcess.getDescription(),
                capstoneProcess.getContents(),
                capstoneProcess.getLink(),
                capstoneProcess.getFeedback(),
                filesDto,
                capstoneProcess.getCreatedAt(),
                capstoneProcess.getUpdatedAt()
        );
    }
}
