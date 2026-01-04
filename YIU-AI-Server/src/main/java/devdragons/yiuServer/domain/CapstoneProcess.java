package devdragons.yiuServer.domain;

import devdragons.yiuServer.domain.state.CapstoneProcessCategory;
import devdragons.yiuServer.domain.state.StatusCategory;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CapstoneProcess {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Integer id;

    @ManyToOne
    private Capstone capstone;

    @Column(nullable = false)
    private CapstoneProcessCategory category;

    @Column(nullable = false)
    private StatusCategory status;

    @Column(nullable = false)
    private String description;

    @Column
    private String contents;

    @Column
    private String link;

    @Column
    private String feedback;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
