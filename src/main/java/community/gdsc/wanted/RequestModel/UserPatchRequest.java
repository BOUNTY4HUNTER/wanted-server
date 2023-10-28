package community.gdsc.wanted.RequestModel;

import jakarta.validation.constraints.NotEmpty;
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
	@NotEmpty(message = "프로필 사진 url.")
	private String profileImageURL;
	@NotEmpty(message = "비밀번호는 필수 입력값입니다.")
	@Size(min = 8, max = 40)
	@Pattern(regexp = "^[ -~]+$")
	private String password;

	@NotEmpty(message = "닉네임은 필수 입력값입니다.")
	private String nickname;

	@NotEmpty(message = "'광역시/도'는 필수 입력값입니다.")
	private String regionDepth1;

	@NotEmpty(message = "'시/군/구' 필수 입력값입니다.")
	private String regionDepth2;

	@NotEmpty(message = "'읍/면/동'은 필수 입력값입니다.")
	private String regionDepth3;
}
