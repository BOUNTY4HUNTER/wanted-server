package community.gdsc.wanted.dto;

import community.gdsc.wanted.domain.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor // 해당 클래스의 모든 멤버 변수(email, password, nickname, profileImage)를 받는 생성자를 생성
public class SignupRequestDTO {
    @NotEmpty(message = "아이디는 필수 입력값입니다.")
    @Size(min = 6, max = 20, message = "유효하지 않은 ID입니다.")
    @Pattern(regexp = "^[a-z0-9]+$", message = "유효하지 않은 ID입니다.")
    private final String username;

    @NotEmpty(message = "비밀번호는 필수 입력값입니다.")
    @Size(min = 8, max = 40, message = "유효하지 않은 비밀번호입니다.")
    @Pattern(regexp = "^[ -~]+$", message = "유효하지 않은 비밀번호입니다.")
    private final String password;

    @NotEmpty(message = "이름은 필수 입력값입니다.")
    @Size(min = 2, max = 20, message = "유효하지 않은 이름입니다.")
    @Pattern(regexp = "^[a-zA-Z가-힣]+$", message = "유효하지 않은 이름입니다.")
    private final String firstName;

    @NotEmpty(message = "성은 필수 입력값입니다.")
    @Size(min = 2, max = 20, message = "유효하지 않은 성입니다.")
    @Pattern(regexp = "^[a-zA-Z가-힣]+$", message = "유효하지 않은 성입니다.")
    private final String lastName;

    @NotEmpty(message = "이메일은 필수 입력값입니다.")
    @Pattern(regexp = "[A-Za-z0-9._%+-]+@inha.(ac.kr|edu)", message = "유효하지 않은 이메일입니다.")
    private final String email;

    @NotEmpty(message = "닉네임은 필수 입력값입니다.")
    @Pattern(regexp = "^[a-zA-Z가-힣0-9]+$", message = "유효하지 않은 닉네임입니다.")
    @Size(min = 2, max = 10)
    private final String nickname;

    @NotEmpty(message = "'광역시/도'는 필수 입력값입니다.")
    @Pattern(regexp = "^[가-힣]+$", message = "유효하지 않은 광역시/도 입력입니다.")
    @Size(min = 2, max = 20, message = "유효하지 않은 광역시/도 입력입니다.")
    private final String regionDepth1;

    @NotEmpty(message = "'시/군/구'는 필수 입력값입니다.")
    @Pattern(regexp = "^[가-힣]+$", message = "유효하지 않은 '시/군/구' 입력입니다.")
    @Size(min = 2, max = 20, message = "유효하지 않은 '시/군/구' 입력입니다.")
    private final String regionDepth2;

    @NotEmpty(message = "'읍/면/동'은 필수 입력값입니다.")
    @Pattern(regexp = "^[가-힣]+$", message = "유효하지 않은 '읍/면/동' 입력입니다.")
    @Size(min = 2, max = 20, message = "유효하지 않은 '읍/면/동' 입력입니다.")
    private final String regionDepth3;

    public User toEntity() {
        return User.builder()
            .username(username)
            .password(password)
            .lastName(lastName)
            .firstName(firstName)
            .email(email)
            .nickname(nickname)
            .regionDepth1(regionDepth1)
            .regionDepth2(regionDepth2)
            .regionDepth3(regionDepth3)
            .build();
    }
}
