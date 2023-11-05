package community.gdsc.wanted.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import community.gdsc.wanted.auth.TokenProvider;
import community.gdsc.wanted.domain.User;
import community.gdsc.wanted.dto.SigninRequestDTO;
import community.gdsc.wanted.dto.SignupRequestDTO;
import community.gdsc.wanted.dto.UserPatchRequestDTO;
import community.gdsc.wanted.exception.NotFoundException;
import community.gdsc.wanted.exception.UnauthorizedException;
import community.gdsc.wanted.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username)
        throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        return new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPassword(),
            AuthorityUtils.createAuthorityList("USER")
        );
    }

    // 회원가입
    public void createUser(SignupRequestDTO signupRequestDTO)
        throws IllegalArgumentException {
        User user = signupRequestDTO.toEntity();

        String plainPassword = signupRequestDTO.getPassword();
        user.setPassword(passwordEncoder.encode(plainPassword));

        userRepository.save(user);
    }

    // 아이디중복확인
    public boolean isUsernameAvailable(String username) {
        return userRepository.findByUsername(username) == null;
    }

    // 유저 아이디를 이용해 User를 리턴받는 메서드
    public User getUserProfile(int id, String authHeader)
        throws NotFoundException, UnauthorizedException {
        return getAuthenticatedUser(id, authHeader);
    }

    public void modifyUser(int id, UserPatchRequestDTO userPatchRequestDTO, String authHeader)
        throws NotFoundException, UnauthorizedException {
        User user = getAuthenticatedUser(id, authHeader);

        entityManager.detach(user);

        if (!userPatchRequestDTO.getNickname().isBlank())
            user.setNickname(userPatchRequestDTO.getNickname());
        if (!userPatchRequestDTO.getPassword().isBlank())
            user.setPassword(userPatchRequestDTO.getPassword());
        if (!userPatchRequestDTO.getRegionDepth1().isBlank())
            user.setRegionDepth1(userPatchRequestDTO.getRegionDepth1());
        if (!userPatchRequestDTO.getRegionDepth2().isBlank())
            user.setRegionDepth2(userPatchRequestDTO.getRegionDepth2());
        if (!userPatchRequestDTO.getRegionDepth3().isBlank())
            user.setRegionDepth3(userPatchRequestDTO.getRegionDepth3());

        userRepository.save(user);
    }

    //id를 이용해 user를 삭제하는 메서드
    public void removeUserById(int id, String authHeader)
        throws NotFoundException, UnauthorizedException {
        User user = getAuthenticatedUser(id, authHeader);

        user.setIsDeleted(true);
        userRepository.save(user);
    }

    public String createToken(SigninRequestDTO signinData) throws NotFoundException {
        User user = userRepository.findByUsername(signinData.getUsername());

        if (user == null || Boolean.TRUE.equals(user.getIsDeleted())) {
            throw new NotFoundException();
        }

        UsernamePasswordAuthenticationToken authToken =
            new UsernamePasswordAuthenticationToken(
                signinData.getUsername(),
                signinData.getPassword()
            );

        Authentication authentication =
            authenticationManagerBuilder.getObject().authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return tokenProvider.createToken(user.getId(), authentication);
    }

    private User getAuthenticatedUser(int id, String authHeader)
        throws NotFoundException, UnauthorizedException {
        User user = userRepository.findById(id);

        if (user == null) {
            throw new NotFoundException();
        }

        if (user.getId() != tokenProvider.getUserIdFromAuthHeader(authHeader)) {
            throw new UnauthorizedException();
        }

        if (Boolean.TRUE.equals(user.getIsDeleted())) {
            throw new NotFoundException();
        }

        return user;
    }
}
