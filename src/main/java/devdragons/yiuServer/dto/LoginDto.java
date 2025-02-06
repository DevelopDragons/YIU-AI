package devdragons.yiuServer.dto;

import devdragons.yiuServer.domain.state.UserEntranceCategory;
import devdragons.yiuServer.domain.state.UserRoleCategory;
import devdragons.yiuServer.domain.state.UserStatusCategory;
import devdragons.yiuServer.domain.state.UserTrackCategory;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class LoginDto {
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
    private TokenDto token;
}