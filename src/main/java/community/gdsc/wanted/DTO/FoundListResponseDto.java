package community.gdsc.wanted.DTO;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FoundListResponseDto {
    private final Integer id;

    private final String title;

    private final Timestamp createAt;
}