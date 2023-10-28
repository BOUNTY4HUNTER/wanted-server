package community.gdsc.wanted.controller;

import community.gdsc.wanted.Base.BaseException;
import community.gdsc.wanted.Base.BaseResponse;
import community.gdsc.wanted.RequestModel.SignupRequest;
import community.gdsc.wanted.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.*;
import static community.gdsc.wanted.Base.BaseResponseStatus.*;
import static community.gdsc.wanted.utils.ValidationEmail.isRegexEmail;


@Controller
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;

    /**
     * 회원가입 API
     * [POST] /users
     */
    @ResponseBody
    @PostMapping("/")    // POST 방식의 요청을 매핑하기 위한 어노테이션
    public BaseResponse<SignupRequest> createUser(@Valid @RequestBody SignupRequest postUserReq) {
        try {
         //email에 값이 존재하는지, 빈 값으로 요청하지는 않았는지 검사합니다. 빈값으로 요청했다면 에러 메시지를 보냅니다.
        if (postUserReq.getEmail() == null) {
            return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
        }
        //이메일 정규표현: 입력받은 이메일이 email@domain.xxx와 같은 형식인지 검사합니다. 형식이 올바르지 않다면 에러 메시지를 보냅니다.
        if (!isRegexEmail(postUserReq.getEmail())) {
            return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
        }
        String pw2 = postUserReq.getPasswordConfirm();
        //비밀번호중복확인(비밀번호 2번입력 확인차)
        if(!postUserReq.getPassword().equals(pw2)) {
            return new BaseResponse<>(INVALID_USER_PASSWORD);
        }

            SignupRequest postUserRes = userService.createUser(postUserReq);
            return new BaseResponse<>(postUserRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }
}
