package devdragons.yiuServer.domain.state;

import lombok.Getter;

@Getter
public enum StatusCategory {
    NOT_SUBMITTED,
    SUBMITTED,
    PASS,
    REJECTED,
}
