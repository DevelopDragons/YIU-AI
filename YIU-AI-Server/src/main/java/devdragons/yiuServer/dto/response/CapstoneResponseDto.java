package devdragons.yiuServer.dto.response;

import devdragons.yiuServer.domain.Capstone;
import devdragons.yiuServer.domain.CapstoneMember;
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
public class CapstoneResponseDto {
    private Integer id;
    private String teamName;
    private List<FileResponseDto> thumbnail;
    private String professor;
    private String topic;
    private String description;
    private String link;
    private List<CapstoneMemberResponseDto> users;
    private LocalDateTime createdAt;

    public static CapstoneResponseDto GetCapstoneDto(Capstone capstone, List<Files> thumbnails, List<CapstoneMember> users) {
        List<CapstoneMemberResponseDto> capstoneMembersResponseDto = users.stream()
                .map(user -> new CapstoneMemberResponseDto(
                        user.getUser().getId(),
                        user.getUser().getName(),
                        user.getUser().getGrade(),
                        user.getUser().getRole(),
                        user.getUser().getStatus(),
                        user.getUser().getMajor(),
                        user.getUser().getDepartment(),
                        user.getUser().getTrack(),
                        user.getUser().getEntrance(),
                        user.getUser().getProfessor(),
                        user.getIsCaptain()
                ))
                .collect(Collectors.toList());

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

        return new CapstoneResponseDto(
                capstone.getId(),
                capstone.getTeamName(),
                thumbnailsDto,
                capstone.getProfessor(),
                capstone.getTopic(),
                capstone.getDescription(),
                capstone.getLink(),
                capstoneMembersResponseDto,
                capstone.getCreatedAt()
        );
    }
}
