package community.gdsc.wanted.dto;

import community.gdsc.wanted.domain.Lost;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WriteRequestDTO {

    private String title;

    private String content;

    private Integer reward;

    private String x;

    private String y;

    private String address;

    public Lost toEntity() {
        return Lost.builder()
            .title(title)
            .content(content)
            .reward(reward)
            .x(x)
            .y(y)
            .address(address)
            .build();
    }
}
