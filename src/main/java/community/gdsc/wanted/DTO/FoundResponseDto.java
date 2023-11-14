package community.gdsc.wanted.DTO;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FoundResponseDto {
    private final Integer id;
    private final String title;
    private final String content;
}
