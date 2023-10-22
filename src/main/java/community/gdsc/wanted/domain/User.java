package community.gdsc.wanted.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name="User")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Column(name="id")
    private int id; //아이디

    @Column(length = 20, name="username")
    private String username;

    @Column(length = 40, name="password")
    private String password;

    @Column(length = 10, name="nickname")
    private String nickname;

    @Column(length = 20, name="last_name")
    private String last_name;

    @Column(length = 20, name="first_name")
    private String first_name;

    @Column(length = 50, name="region_1depth")
    private String region_1depth;

    @Column(length = 50, name="region_2depth")
    private String region_2depth;

    @Column(length = 50, name="region_3depth")
    private String region_3depth;

    @Column(length = 50, name="email")
    private String email;

    @Column(name="coin")
    private int coin;

    @Column(length = 45, name="is_admin")
    private String is_admin;

    @Column(name="created_at")
    @CreatedDate
    private Timestamp created_at;

    @Column(name="is_deleted")
    private int is_deleted;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return id == user.id && coin == user.coin && is_deleted == user.is_deleted && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(nickname, user.nickname) && Objects.equals(last_name, user.last_name) && Objects.equals(first_name, user.first_name) && Objects.equals(region_1depth, user.region_1depth) && Objects.equals(region_2depth, user.region_2depth) && Objects.equals(region_3depth, user.region_3depth) && Objects.equals(email, user.email) && Objects.equals(is_admin, user.is_admin) && Objects.equals(created_at, user.created_at);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, nickname, last_name, first_name, region_1depth, region_2depth, region_3depth, email, coin, is_admin, created_at, is_deleted);
    }

}