package community.gdsc.wanted.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SigninRequestDTO {
    @NotEmpty(message = "아이디는 필수 입력값입니다.")
    @Size(min = 6, max = 20, message = "유효하지 않은 ID입니다.")
    @Pattern(regexp = "^[a-z0-9]+$", message = "유효하지 않은 ID입니다.")
    private final String username;

    @NotEmpty(message = "비밀번호는 필수 입력값입니다.")
    @Size(min = 8, max = 40, message = "유효하지 않은 비밀번호입니다.")
    @Pattern(regexp = "^[ -~]+$", message = "유효하지 않은 비밀번호입니다.")
    private final String password;
}