package community.gdsc.wanted.RequestModel;

import community.gdsc.wanted.domain.User;
import lombok.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;


@Getter // 해당 클래스에 대한 접근자 생성
@Setter // 해당 클래스에 대한 설정자 생성
@AllArgsConstructor // 해당 클래스의 모든 멤버 변수(email, password, nickname, profileImage)를 받는 생성자를 생성
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 해당 클래스의 파라미터가 없는 생성자를 생성, 접근제한자를 PROTECTED로 설정.
public class SignupRequest {

    @NotEmpty(message = "아이디는 필수 입력값입니다.")
    @Size(min = 6, max = 20)
    private String username;
    @NotEmpty(message = "비밀번호는 필수 입력값입니다.")
    @Size(min=6, max=40)
    @Pattern(regexp = "^[ -~]+$")
    private String password;

    @NotEmpty(message = "비밀번호 확인은 필수 입력값입니다.")
    @Size(min=6, max=40)
    @Pattern(regexp = "^[ -~]+$")
    private String passwordConfirm;

    @NotEmpty(message = "이름은 필수 입력값입니다.")
    @Size(min=2, max=20)
    private String firstName;

    @NotEmpty(message = "이름은 필수 입력값입니다.")
    @Size(min=2, max=20)
    private String lastName;

    @NotEmpty(message = "이메일은 필수 입력값입니다.")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
    @Email
    private String email;

    @NotEmpty(message = "닉네임은 필수 입력값입니다.")
    private String nickname;

    @NotEmpty(message = "'광역시/도'는 필수 입력값입니다.")
    private String regionDepth1;

    @NotEmpty(message = "'시/군/구' 필수 입력값입니다.")
    private String regionDepth2;

    @NotEmpty(message = "'읍/면/동'은 필수 입력값입니다.")
    private String regionDepth3;

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