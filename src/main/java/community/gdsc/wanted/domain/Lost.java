package community.gdsc.wanted.domain;

import java.sql.Timestamp;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
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

    @Column(name = "title", length = 100)
    private String title;

    @Column(name = "content", length = 5000)
    private String content;

    @Column(name = "reward")
    private Integer reward;

    @Column(name = "x", length = 20)
    private String x;

    @Column(name = "y", length = 20)
    private String y;

    @Column(name = "address", length = 45)
    private String address;

    @Column(name = "lostcol", length = 45)
    private String lostcol;

    @Column(name = "create_at")
    @CreatedDate
    private Timestamp createdAt;

    @Column(name = "is_deleted")
    private Integer isDeleted;

    @Column(name = "images")
    @Lob
    private byte[] imageData;
}
