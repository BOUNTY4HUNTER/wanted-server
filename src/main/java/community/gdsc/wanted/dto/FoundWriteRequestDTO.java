package community.gdsc.wanted.dto;

import community.gdsc.wanted.domain.Found;
import community.gdsc.wanted.domain.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FoundWriteRequestDTO {
    @NotEmpty(message = "제목은 필수 입력값입니다.")
    @Size(max = 100, message = "제목은 100글자를 넘을 수 없습니다.")
    private final String title;

    @NotEmpty(message = "내용은 필수 입력값입니다.")
    @Size(max = 5000, message = "내용은 5000글자를 넘을 수 없습니다.")
    private final String content;

    private final String x;

    private final String y;

    @Pattern(regexp = "^[가-힣ㄱ-ㅎa-zA-Z0-9\\s,.'-]{1,45}$", message = "유효하지 않은 주소입니다.")
    @Size(max = 45, message = "주소는 45글자를 넘을 수 없습니다.")
    private final String address;

    public Found toEntity(User user) {
        return Found.builder()
            .author(user)
            .title(title)
            .content(content)
            .x(x)
            .y(y)
            .address(address)
            .build();
    }
}