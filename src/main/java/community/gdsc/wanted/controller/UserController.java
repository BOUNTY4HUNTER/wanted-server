package community.gdsc.wanted.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import community.gdsc.wanted.domain.User;
import community.gdsc.wanted.dto.SigninRequestDTO;
import community.gdsc.wanted.dto.SigninResponseDTO;
import community.gdsc.wanted.dto.SignupRequestDTO;
import community.gdsc.wanted.dto.UserInfoResponseDTO;
import community.gdsc.wanted.dto.UserPatchRequestDTO;
import community.gdsc.wanted.exception.NotFoundException;
import community.gdsc.wanted.exception.UnauthorizedException;
import community.gdsc.wanted.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @ResponseBody
    @PostMapping()    // POST 방식의 요청을 매핑하기 위한 어노테이션
    public ResponseEntity<String> createUser( //회원가입
        @RequestBody
        @Valid
        SignupRequestDTO request
    ) throws IllegalArgumentException, IOException {
        userService.createUser(request);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/{userId}")
    public UserInfoResponseDTO getUserInfo(
        @RequestHeader("Authorization")
        String authorizationHeader,
        @PathVariable("userId")
        Integer userId
    ) throws NotFoundException, UnauthorizedException {
        User user = userService.getUserProfile(userId, authorizationHeader);
        return user.toUserInfoResponse();
    }

    @ResponseBody
    @PatchMapping("/{userId}")    // POST 방식의 요청을 매핑하기 위한 어노테이션
    public ResponseEntity<String> patchUser(
        @RequestHeader("Authorization")
        String authorizationHeader,
        @PathVariable("userId")
        Integer userId,
        @Valid
        @RequestBody
        UserPatchRequestDTO request
    ) throws NotFoundException, UnauthorizedException, IOException {
        userService.modifyUser(userId, request, authorizationHeader);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @ResponseBody
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(
        @RequestHeader("Authorization")
        String authorizationHeader,
        @PathVariable("userId")
        Integer userId
    ) throws NotFoundException, UnauthorizedException {
        userService.removeUserById(userId, authorizationHeader);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping("/signin")
    public ResponseEntity<SigninResponseDTO> signin(
        @RequestBody
        SigninRequestDTO request
    ) throws NotFoundException {
        String token = userService.createToken(request);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + token);

        return new ResponseEntity<>(
            new SigninResponseDTO(token),
            httpHeaders,
            HttpStatus.OK
        );
    }

    @ResponseBody
    @GetMapping("/login/forgot/password")
    public ResponseEntity<String> findUserPassword(@RequestParam String id
    ) throws NotFoundException, UnsupportedEncodingException {
        userService.sendForgotPassword(id);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/login/forgot/id")
    public ResponseEntity<String> findUserId(@RequestParam String email
    ) throws NotFoundException, UnsupportedEncodingException {
        userService.sendForgotId(email);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}
