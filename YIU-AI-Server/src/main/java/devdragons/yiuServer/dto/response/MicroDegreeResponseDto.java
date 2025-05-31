package devdragons.yiuServer.dto.response;

import devdragons.yiuServer.domain.MicroDegree;
import devdragons.yiuServer.domain.MicroDegreeSubject;
import devdragons.yiuServer.domain.state.MicroDegreeCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MicroDegreeResponseDto {
    private MicroDegreeCategory category;
    private String description;
    private String title;
    private List<MicroDegreeSubjectResponseDto> subjects;

    public static MicroDegreeResponseDto GetMicroDegreeDto(MicroDegree microDegree, List<MicroDegreeSubject> subjectList) {
        return new MicroDegreeResponseDto(
                microDegree.getCategory(),
                microDegree.getDescription(),
                microDegree.getTitle(),
                subjectList.stream()
                        .map(MicroDegreeSubjectResponseDto::from)
                        .collect(Collectors.toList())
        );
    }
}
