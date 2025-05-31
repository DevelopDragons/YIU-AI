package devdragons.yiuServer.domain.state;

import lombok.Getter;

@Getter
public enum ProfessorTypeCategory {
    FULL_TIME,
    ADJUNCT,
    INVITED,
    VISITING,
    RETIRED;
}
