package community.gdsc.wanted.domain;

import java.sql.Timestamp;

import org.springframework.data.annotation.CreatedDate;

import community.gdsc.wanted.dto.ListResponseDTO;
import community.gdsc.wanted.dto.ViewResponseDTO;
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
@Table(name = "lost")
@Entity
@Data
public class Lost {
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

    @Column(name = "reward", nullable = false)
    private Integer reward;

    @Column(name = "x", length = 20)
    private String x;

    @Column(name = "y", length = 20)
    private String y;

    @Column(name = "address", length = 45)
    private String address;

    @Column(name = "created_at", updatable = false)
    @CreatedDate
    private Timestamp createdAt;

    @Column(name = "is_deleted", nullable = false)
    private Integer isDeleted;

    public ViewResponseDTO toViewResponse() {
        return new ViewResponseDTO(
            id,
            title,
            content,
            reward,
            x,
            y,
            address);
    }

    public ListResponseDTO toListResponse() {
        return new ListResponseDTO(
            this.getId(),
            this.getTitle(),
            this.getReward(),
            this.getCreatedAt()
        );
    }
}
