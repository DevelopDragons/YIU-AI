package devdragons.yiuServer.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Info {
    @Id
    @Column(unique = true)
    private String name;

    @Column(nullable = false)
    private String engName;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String tel;

    @Column(nullable = false)
    private String mail;

    @Column(nullable = false)
    private String professor;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String greeting;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String contents;
}
