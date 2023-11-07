package community.gdsc.wanted.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WriteRequestDto {
    private String title;
    private String content;
    private Integer isDeleted = 0;
}