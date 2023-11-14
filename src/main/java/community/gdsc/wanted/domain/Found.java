package community.gdsc.wanted.domain;

import java.sql.Timestamp;

import org.springframework.data.annotation.CreatedDate;

import community.gdsc.wanted.dto.FoundListResponseDto;
import community.gdsc.wanted.dto.FoundResponseDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "found")
@Entity
@Data
public class Found {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "author_idx")
    private Integer authorIdx;

    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @Column(name = "content", length = 5000, nullable = false)
    private String content;

    @Column(name = "created_at", updatable = false)
    @CreatedDate
    private Timestamp created_at;

    @Column(name = "is_deleted", nullable = false)
    @Builder.Default
    private Integer is_deleted = 0;

    public FoundResponseDto toViewResponse() {
        return new FoundResponseDto(
            id,
            title,
            content
        );
    }

    public FoundListResponseDto toListResponse() {
        return new FoundListResponseDto(
            this.getId(),
            this.getTitle(),
            this.getCreated_at()
        );
    }
}