package community.gdsc.wanted.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListResponseDTO {
    private Integer id;
    private String title;
    private Integer reward;
    private Timestamp createAt;
}
