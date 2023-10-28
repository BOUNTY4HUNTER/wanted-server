package community.gdsc.wanted.domain;

import java.sql.Timestamp;

import org.springframework.data.annotation.CreatedDate;

import community.gdsc.wanted.RequestModel.SignupRequest;
import community.gdsc.wanted.ResponseModel.UserAllInfoResponse;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user")
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

	@Column(length = 20, unique = true)
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

	@Column(length = 50, unique = true)
	private String email;

	@Column(name = "coin")
	private Integer coin;

	@Column(name = "is_admin")
	private Boolean isAdmin;

	@Column(name = "created_at")
	@CreatedDate
	private Timestamp createdAt;

	@Column(name = "is_deleted")
	private Boolean isDeleted;

	public SignupRequest toPostUserRes() {
		return new SignupRequest(username,
			password,
			password,
			firstName,
			lastName,
			email,
			nickname,
			regionDepth1,
			regionDepth2,
			regionDepth3);
	}

	public UserAllInfoResponse toUserAllInfoResponse() {
		String baseImageURL = "https://sssdsfsd";
		return new UserAllInfoResponse(id,
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
}