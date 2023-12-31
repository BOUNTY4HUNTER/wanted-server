package community.gdsc.wanted.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LostModifyRequestDTO {
    @Size(max = 100, message = "제목은 100글자를 넘을 수 없습니다.")
    private final String title;

    @Size(max = 5000, message = "내용은 5000글자를 넘을 수 없습니다.")
    private final String content;

    @Positive(message = "사례금은 양수의 값입니다.")
    private final Integer reward;

    @Pattern(regexp = "^[-+]?([1-8]?\\d(\\.\\d+)?|90(\\.0+)?)$", message = "유효하지 않은 위도입니다.")
    private final String x;

    @Pattern(regexp = "^[-+]?((1[0-7]\\d(\\.\\d+)?)|180(\\.0+)?)$", message = "유효하지 않은 경도입니다.")
    private final String y;

    @Pattern(regexp = "^[가-힣ㄱ-ㅎa-zA-Z0-9\\s,.'-]{1,45}$", message = "유효하지 않은 주소입니다.")
    @Size(max = 45, message = "주소는 45글자를 넘을 수 없습니다.")
    private final String address;
}
