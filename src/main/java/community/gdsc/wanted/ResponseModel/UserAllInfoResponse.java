package community.gdsc.wanted.ResponseModel;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter // 해당 클래스에 대한 접근자 생성
@Setter // 해당 클래스에 대한 설정자 생성
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserAllInfoResponse {
	private Integer id; //아이디

	private String username;

	private String profileImageURL;

	private String last_name;

	private String first_name;

	private String email;

	private String nickname;

	private String region_depth_1;

	private String region_depth_2;

	private String region_depth_3;
}
