package devdragons.yiuServer.dto.request;

import devdragons.yiuServer.domain.state.UserEntranceCategory;
import devdragons.yiuServer.domain.state.UserRoleCategory;
import devdragons.yiuServer.domain.state.UserStatusCategory;
import devdragons.yiuServer.domain.state.UserTrackCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {
    private String id;
    private String name;
    private String pwd;
    private Integer grade;
    private UserRoleCategory role;
    private UserStatusCategory status;
    private String major;
    private String department;
    private UserTrackCategory track;
    private UserEntranceCategory entrance;
    private String professor;
}
