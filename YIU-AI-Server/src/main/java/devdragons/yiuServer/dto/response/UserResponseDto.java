package devdragons.yiuServer.dto.response;

import devdragons.yiuServer.domain.User;
import devdragons.yiuServer.domain.state.UserEntranceCategory;
import devdragons.yiuServer.domain.state.UserRoleCategory;
import devdragons.yiuServer.domain.state.UserStatusCategory;
import devdragons.yiuServer.domain.state.UserTrackCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private String id;
    private String name;
    private Integer grade;
    private UserRoleCategory role;
    private UserStatusCategory status;
    private String major;
    private String department;
    private UserTrackCategory track;
    private UserEntranceCategory entrance;
    private String professor;

    public static UserResponseDto GetUserDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getName(),
                user.getGrade(),
                user.getRole(),
                user.getStatus(),
                user.getMajor(),
                user.getDepartment(),
                user.getTrack(),
                user.getEntrance(),
                user.getProfessor()
        );
    }
}
