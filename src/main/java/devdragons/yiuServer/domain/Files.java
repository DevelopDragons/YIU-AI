package devdragons.yiuServer.domain;

import devdragons.yiuServer.domain.state.FileType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Files {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private FileType type;

    @Column(nullable = false)
    private Integer typeId;

    @Column(nullable = false)
    private String category;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String originName;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String saveName;

    @Column(nullable = false)
    private Long size;

    @CreationTimestamp
    @Column
    private LocalDateTime createdAt;
}
