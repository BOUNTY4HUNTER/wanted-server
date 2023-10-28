package community.gdsc.wanted.RequestModel;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter // 해당 클래스에 대한 접근자 생성
@Setter // 해당 클래스에 대한 설정자 생성
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserPatchRequest {
	private String profileImageURL;

	@Size(min = 8, max = 40)
	@Pattern(regexp = "^[ -~]+$")
	private String password;

	@Pattern(regexp = "^[a-z가-힣0-9]+$", message = "닉네임 형식에 맞지 않습니다.")
	@Size(min = 2, max = 10)
	private String nickname;

	@Pattern(regexp = "^[가-힣]+$", message = "광역시/도 형식에 맞지 않습니다.")
	@Size(min = 2, max = 20)
	private String regionDepth1;

	@Pattern(regexp = "^[가-힣]+$", message = "시/군/구 형식에 맞지 않습니다.")
	@Size(min = 2, max = 20)
	private String regionDepth2;

	@Pattern(regexp = "^[가-힣]+$", message = "읍/면/동 형식에 맞지 않습니다.")
	@Size(min = 2, max = 20)
	private String regionDepth3;
}
