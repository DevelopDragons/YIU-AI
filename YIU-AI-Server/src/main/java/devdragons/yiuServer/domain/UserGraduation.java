package devdragons.yiuServer.domain;

import devdragons.yiuServer.domain.state.StatusCategory;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserGraduation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Integer id;

    @ManyToOne
    private User user;

    @ManyToOne
    private GraduationDetail graduationDetail;

    @Column(nullable = false)
    private StatusCategory status;

    @Column(columnDefinition = "TEXT")
    private String feedback;

    @Column
    private String description;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;
}
