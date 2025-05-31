package devdragons.yiuServer.domain.state;

import lombok.Getter;

@Getter
public enum UserStatusCategory {
    STUDENT,
    GRADUATE,
    COMPLETION,
    DISMISSAL,
    ABSENCE;
}
