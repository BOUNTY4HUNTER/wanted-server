package community.gdsc.wanted.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LostResponseDTO {
    private final Integer id;
    private final String title;
    private final String content;
    private final Integer reward;
    private final String x;
    private final String y;
    private final String address;
}
