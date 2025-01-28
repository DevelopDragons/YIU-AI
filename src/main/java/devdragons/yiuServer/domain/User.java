package devdragons.yiuServer.domain;

import devdragons.yiuServer.domain.state.UserEntranceCategory;
import devdragons.yiuServer.domain.state.UserRoleCategory;
import devdragons.yiuServer.domain.state.UserStatusCategory;
import devdragons.yiuServer.domain.state.UserTrackCategory;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @Column(unique = true)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String pwd;

    @Column(nullable = false)
    private Integer grade;

    @Column(nullable = false)
    private UserRoleCategory role;

    @Column(nullable = false)
    private UserStatusCategory status;

    @Column(nullable = false)
    private String major;

    @Column(nullable = false)
    private String department;

    @Column(nullable = false)
    private UserTrackCategory track;

    @Column(nullable = false)
    private UserEntranceCategory entrance;

    @Column(nullable = false)
    private String professor;

    @Column(columnDefinition = "TEXT")
    private String refreshToken;

    @CreationTimestamp
    @Column
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column
    private LocalDateTime updatedAt;
}
