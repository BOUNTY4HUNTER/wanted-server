package community.gdsc.wanted.controller;

import static community.gdsc.wanted.Base.BaseResponseStatus.*;
import static community.gdsc.wanted.utils.ValidationEmail.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import community.gdsc.wanted.Base.BaseException;
import community.gdsc.wanted.Base.BaseResponse;
import community.gdsc.wanted.RequestModel.SignupRequest;
import community.gdsc.wanted.RequestModel.UserPatchRequest;
import community.gdsc.wanted.ResponseModel.UserAllInfoResponse;
import community.gdsc.wanted.domain.User;
import community.gdsc.wanted.service.UserService;
import jakarta.validation.Valid;

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
			if (!postUserReq.getPassword().equals(pw2)) {
				return new BaseResponse<>(INVALID_USER_PASSWORD);
			}

			SignupRequest postUserRes = userService.createUser(postUserReq);
			return new BaseResponse<>(postUserRes);
		} catch (BaseException exception) {
			return new BaseResponse<>((exception.getStatus()));
		}
	}

	@ResponseBody
	@PatchMapping("/{userId}")    // POST 방식의 요청을 매핑하기 위한 어노테이션
	public BaseResponse<UserPatchRequest> patchUser(@PathVariable("userId") Integer userId,
		@Valid @RequestBody UserPatchRequest postUserReq) {

		User baseuser = userService.findUserById(userId);
		String newImageURL = postUserReq.getProfileImageURL();
		String newPassword = postUserReq.getPassword();
		String newNickname = postUserReq.getNickname();
		String newRegionDepth1 = postUserReq.getRegionDepth1();
		String newRegionDepth2 = postUserReq.getRegionDepth2();
		String newRegionDepth3 = postUserReq.getRegionDepth3();

		userService.modifyUser(baseuser, newImageURL, newPassword, newNickname, newRegionDepth1, newRegionDepth2,
			newRegionDepth3);

		return new BaseResponse<>(postUserReq);
	}

	@ResponseBody
	@GetMapping("/{userId}")
	public BaseResponse<UserAllInfoResponse> getUserAllInfo(@PathVariable("userId") Integer userId) {
		User baseuser = userService.findUserById(userId);
		UserAllInfoResponse userAllInfoResponse = baseuser.toUserAllInfoResponse();
		// TODO: userAllInfoResponse.setProfileImageURL(); 프로필 여기서 세팅 해주기! -> 기존에는 baseURL로 되어있음

		return new BaseResponse<>(userAllInfoResponse);
	}

	@ResponseBody
	@DeleteMapping("/{userId}")
	public BaseResponse<String> deleteUser(@PathVariable("userId") Integer userId) {
		String result = "사용자 정보가 성공적으로 삭제되었습니다";
		userService.removeUserById(userId);
		return new BaseResponse<>(result);
	}
}
