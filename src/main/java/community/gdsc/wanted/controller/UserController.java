package community.gdsc.wanted.controller;

import community.gdsc.wanted.auth.TokenProvider;
import community.gdsc.wanted.exception.UnauthorizedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import community.gdsc.wanted.domain.User;
import community.gdsc.wanted.dto.SigninRequestDTO;
import community.gdsc.wanted.dto.SigninResponseDTO;
import community.gdsc.wanted.dto.SignupRequestDTO;
import community.gdsc.wanted.dto.UserInfoResponseDTO;
import community.gdsc.wanted.dto.UserPatchRequestDTO;
import community.gdsc.wanted.exception.NotFoundException;
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
    public ResponseEntity<String> createUser(
        @RequestBody
        @Valid
        SignupRequestDTO request
    ) throws IllegalArgumentException {
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
    ) throws NotFoundException, UnauthorizedException {
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
}
