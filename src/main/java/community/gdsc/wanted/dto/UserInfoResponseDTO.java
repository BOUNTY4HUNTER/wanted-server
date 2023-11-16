package community.gdsc.wanted.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter // 해당 클래스에 대한 접근자 생성
@Setter // 해당 클래스에 대한 설정자 생성
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserInfoResponseDTO {
    private Integer id; //아이디

    private String username;

    private String profileImageURL;

    private String lastName;

    private String firstName;

    private String email;

    private String nickname;

    private String regionDepth1;

    private String regionDepth2;

    private String regionDepth3;
}
