package community.gdsc.wanted.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UserPatchRequestDTO {
    private final String profileImageURL;

    @Size(min = 8, max = 40)
    @Pattern(regexp = "^[ -~]+$")
    private final String password;

    @Pattern(regexp = "^[a-z가-힣0-9]+$", message = "닉네임 형식에 맞지 않습니다.")
    @Size(min = 2, max = 10)
    private final String nickname;

    @Pattern(regexp = "^[가-힣]+$", message = "광역시/도 형식에 맞지 않습니다.")
    @Size(min = 2, max = 20)
    private final String regionDepth1;

    @Pattern(regexp = "^[가-힣]+$", message = "시/군/구 형식에 맞지 않습니다.")
    @Size(min = 2, max = 20)
    private final String regionDepth2;

    @Pattern(regexp = "^[가-힣]+$", message = "읍/면/동 형식에 맞지 않습니다.")
    @Size(min = 2, max = 20)
    private final String regionDepth3;
}
