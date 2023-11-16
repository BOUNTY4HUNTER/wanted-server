package community.gdsc.wanted.dto;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FoundListResponseDTO {
    private final Integer id;

    private final String title;

    private final Timestamp createAt;
}