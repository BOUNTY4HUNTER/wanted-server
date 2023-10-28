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
	
	private String nickname;

	private String regionDepth1;

	private String regionDepth2;

	private String regionDepth3;
}
