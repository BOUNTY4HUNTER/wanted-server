package community.gdsc.wanted.domain;

import java.sql.Timestamp;
import java.util.Objects;

import org.hibernate.annotations.CreationTimestamp;

import community.gdsc.wanted.dto.UserInfoResponseDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id; //아이디

    @Column(length = 20, unique = true, nullable = false)
    private String username;

    @Column(length = 72, nullable = false)
    private String password;

    @Column(length = 10, nullable = false)
    private String nickname;

    @Column(length = 20, name = "last_name", nullable = false)
    private String lastName;

    @Column(length = 20, name = "first_name", nullable = false)
    private String firstName;

    @Column(length = 50, name = "region_depth_1", nullable = false)
    private String regionDepth1;

    @Column(length = 50, name = "region_depth_2", nullable = false)
    private String regionDepth2;

    @Column(length = 50, name = "region_depth_3", nullable = false)
    private String regionDepth3;

    @Column(length = 50, unique = true, nullable = false)
    private String email;

    @Column(name = "coin")
    @Builder.Default
    private Integer coin = 0;

    @Column(name = "is_admin")
    @Builder.Default
    private Boolean isAdmin = false;

    @Column(name = "created_at")
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name = "is_deleted")
    @Builder.Default
    private Boolean isDeleted = false;

    public UserInfoResponseDTO toUserInfoResponse() {
        String baseImageURL = "https://sssdsfsd";
        return new UserInfoResponseDTO(
            id,
            username,
            baseImageURL,
            lastName,
            firstName,
            email,
            nickname,
            regionDepth1,
            regionDepth2,
            regionDepth3);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        User target = (User)o;

        return Objects.equals(username, target.username)
            && Objects.equals(nickname, target.nickname)
            && Objects.equals(lastName, target.lastName)
            && Objects.equals(firstName, target.firstName)
            && Objects.equals(regionDepth1, target.regionDepth1)
            && Objects.equals(regionDepth2, target.regionDepth2)
            && Objects.equals(regionDepth3, target.regionDepth3)
            && Objects.equals(isAdmin, target.isAdmin)
            && Objects.equals(isDeleted, target.isDeleted)
            && Objects.equals(coin, target.coin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}