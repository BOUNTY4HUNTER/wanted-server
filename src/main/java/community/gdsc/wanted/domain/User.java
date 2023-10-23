package community.gdsc.wanted.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GeneratedColumn;
import org.springframework.data.annotation.CreatedDate;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name="user")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id; //아이디

    @Column(length = 20)
    private String username;

    @Column(length = 40)
    private String password;

    @Column(length = 10)
    private String nickname;

    @Column(length = 20, name = "last_name")
    private String lastName;

    @Column(length = 20, name = "first_name")
    private String firstName;

    @Column(length = 50, name = "region_depth_1")
    private String regionDepth1;

    @Column(length = 50, name = "region_depth_2")
    private String regionDepth2;

    @Column(length = 50, name = "region_depth_3")
    private String regionDepth3;

    @Column(length = 50)
    private String email;

    @Column(name = "coin")
    private Integer coin;

    @Column(length = 45, name = "is_admin")
    private Boolean isAdmin;

    @Column(name = "created_at")
    @CreatedDate
    private Timestamp createdAt;

    @Column(name = "is_deleted")
    private Integer isDeleted;
}