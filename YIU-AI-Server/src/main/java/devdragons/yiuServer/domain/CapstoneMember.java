package devdragons.yiuServer.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CapstoneMember {
    @Id
    private Integer capstoneId;

    @MapsId("capstoneId")
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "capstone_id")
    private Capstone capstone;

    @OneToOne(fetch = FetchType.EAGER)
    private User user;

    @Column(nullable = false)
    private Integer isCaptain; // 캡틴인 경우 1, 일반 멤버인 경우 0 값 사용
}
