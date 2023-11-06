package community.gdsc.wanted.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViewResponseDTO {
    private Integer id;
    private String title;
    private String content;
    private Integer reward;
    private String x;
    private String y;
    private String address;
}
