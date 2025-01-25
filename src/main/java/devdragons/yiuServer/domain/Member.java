package devdragons.yiuServer.domain;

import devdragons.yiuServer.domain.state.MemberRoleCategory;
import devdragons.yiuServer.domain.state.ProfessorTypeCategory;
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
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String mail;

    @Column(nullable = false)
    private String tel;

    @Column(nullable = false)
    private String labName;

    @Column(nullable = false)
    private String labLink;

    @Column(nullable = false)
    private String labCategory;

    @Column(nullable = false)
    private ProfessorTypeCategory type;

    @Column(nullable = false)
    private MemberRoleCategory role;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @CreationTimestamp
    @Column
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column
    private LocalDateTime updatedAt;
}
