package devdragons.yiuServer.domain;

import devdragons.yiuServer.domain.state.ClassCategory;
import devdragons.yiuServer.domain.state.CourseCategory;
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
public class Curriculum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private CourseCategory course;

    @Column(nullable = false)
    private Integer grade;

    @Column(nullable = false)
    private Integer term;

    @Column(nullable = false)
    private Integer credit;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(nullable = false)
    private ClassCategory classes;

    @Column(nullable = false)
    private Integer practice;

    @Column(nullable = false)
    private Integer theory;

    @Column(nullable = false)
    private String code;

    @CreationTimestamp
    @Column
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column
    private LocalDateTime updatedAt;
}
