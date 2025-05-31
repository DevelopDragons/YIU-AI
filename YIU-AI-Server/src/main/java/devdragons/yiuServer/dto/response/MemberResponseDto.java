package devdragons.yiuServer.dto.response;

import devdragons.yiuServer.domain.Files;
import devdragons.yiuServer.domain.Member;
import devdragons.yiuServer.domain.state.MemberRoleCategory;
import devdragons.yiuServer.domain.state.ProfessorTypeCategory;
import devdragons.yiuServer.domain.state.RequiredCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponseDto {
    private Integer id;
    private String name;
    private List<FileResponseDto> image;
    private String mail;
    private String tel;
    private String labName;
    private List<FileResponseDto> labImage;
    private String labLink;
    private String labCategory;
    private ProfessorTypeCategory type;
    private MemberRoleCategory role;
    private String description;
    private RequiredCategory required;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static MemberResponseDto GetMemberDto(Member member, List<Files> image, List<Files> labImage) {
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

        List<FileResponseDto> labImageDto = labImage.stream()
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

        return new MemberResponseDto(
                member.getId(),
                member.getName(),
                imageDto,
                member.getMail(),
                member.getTel(),
                member.getLabName(),
                labImageDto,
                member.getLabLink(),
                member.getLabCategory(),
                member.getType(),
                member.getRole(),
                member.getDescription(),
                member.getRequired(),
                member.getCreatedAt(),
                member.getUpdatedAt()
        );
    }
}
