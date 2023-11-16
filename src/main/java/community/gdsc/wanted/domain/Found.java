package community.gdsc.wanted.domain;

import java.sql.Timestamp;

import org.springframework.data.annotation.CreatedDate;

import community.gdsc.wanted.dto.FoundListResponseDTO;
import community.gdsc.wanted.dto.FoundResponseDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @Column(name = "content", length = 5000, nullable = false)
    private String content;

    @Column(name = "x", length = 20, nullable = true)
    private String x;

    @Column(name = "y", length = 20, nullable = true)
    private String y;

    @Column(name = "address", length = 45, nullable = true)
    private String address;

    @Column(name = "created_at", updatable = false)
    @CreatedDate
    private Timestamp createdAt;

    @Column(name = "is_deleted", nullable = false)
    @Builder.Default
    private Boolean isDeleted = false;

    public FoundResponseDTO toViewResponse() {
        return new FoundResponseDTO(
            id,
            title,
            content
        );
    }

    public FoundListResponseDTO toListResponse() {
        return new FoundListResponseDTO(
            this.getId(),
            this.getTitle(),
            this.getCreatedAt()
        );
    }
}