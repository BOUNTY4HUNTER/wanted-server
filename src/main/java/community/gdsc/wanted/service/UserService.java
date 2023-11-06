package community.gdsc.wanted.service;

import java.io.UnsupportedEncodingException;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
import community.gdsc.wanted.repository.UserRepository;
import jakarta.mail.internet.InternetAddress;
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

    private final JavaMailSender mailSender;

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
    public User getUserProfile(int id) throws NotFoundException {
        User user = userRepository.findById(id);

        if (user == null || user.getIsDeleted()) {
            throw new NotFoundException();
        }

        return user;
    }

    public void modifyUser(int id, UserPatchRequestDTO userPatchRequestDTO)
        throws NotFoundException {
        User user = userRepository.findById(id);

        if (user == null || user.getIsDeleted()) {
            throw new NotFoundException();
        }

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
    public void removeUserById(int id) throws NotFoundException {
        User user = userRepository.findById(id);
        if (user == null || user.getIsDeleted())
            throw new NotFoundException();

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

        return tokenProvider.createToken(authentication);
    }

    //아이디 잃어버렸을 때 메일 보내고
    public String sendForgotId(String email) throws MailAuthenticationException {

        User user = userRepository.findByEmail(email);

        if(user==null){
            throw new NoSuchElementException();
        }else{

            String id = user.getNickname();

            //메세지를 생성하고 보낼 메일 설정 저장
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            try {
                message.setFrom(String.valueOf(new InternetAddress("yunjinyong7302000@gmail.com","💰WANTED","UTF-8")));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            message.setSubject("💰[WANTED] Your ID is here!");
            message.setText("💰[WANTED] Hello "+ user.getNickname() + "Your ID is" + id);
            mailSender.send(message);
            return "User's ID sent to your email.";
        }
    }

    //비밀번호 잃어버렸을 때 메일 보내고
    public String sendForgotPassword(String username) throws MailAuthenticationException {
        User user = userRepository.findByUsername(username);

        System.out.println(username);

        String email = user.getEmail();

        System.out.println(email);

        if(user==null){
            throw new NoSuchElementException();
        }else{
            String tempPassword = getTempPassword();

            System.out.println(tempPassword);

            user.setPassword(passwordEncoder.encode(tempPassword));
            userRepository.save(user);

            //메세지를 생성하고 보낼 메일 설정 저장
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            try {
                message.setFrom(String.valueOf(new InternetAddress("yunjinyong7302000@gmail.com","💰WANTED","UTF-8")));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            message.setSubject("💰[WANTED] New Temporary Password is here!");
            message.setText("💰[WANTED] Hello "+ user.getNickname()+"! We send your temporary password here. \nBut this is not secured so please change password once you sign into our site. \nPassword : " + tempPassword);
            mailSender.send(message);
            return "Temporary password sent to your email.";
        }
    }

    //임시 비밀번호 발급
    public String getTempPassword(){
        char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
            'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

        String str = "";

        int idx = 0;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            str += charSet[idx];
        }
        return str;
    }


}
