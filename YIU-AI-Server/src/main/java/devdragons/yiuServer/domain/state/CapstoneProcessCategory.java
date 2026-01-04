package devdragons.yiuServer.domain.state;

import lombok.Getter;

@Getter
public enum CapstoneProcessCategory {
    BEGIN_DOCUMENT, // 기획 자료
    BEGIN_VIDEO, // 기획 영상
    MIDDLE_DOCUMENT, // 중간 자료
    MIDDLE_VIDEO, // 중간 영상
    FINAL_DOCUMENT, // 최종 자료
    FINAL_VIDEO, // 최종 영상
    END_DOCUMENT, // 졸업 자료
    END_VIDEO, // 졸업 영상
    ETC // 기타
}
