package community.gdsc.wanted.service;

import community.gdsc.wanted.Base.BaseException;
import community.gdsc.wanted.RequestModel.SignupRequest;
import community.gdsc.wanted.domain.User;
import community.gdsc.wanted.repository.UserRepository;
import community.gdsc.wanted.config.secret.Secret;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import community.gdsc.wanted.utils.AES128;

import static community.gdsc.wanted.Base.BaseResponseStatus.DATABASE_ERROR;
import static community.gdsc.wanted.Base.BaseResponseStatus.*;

@Transactional
@RequiredArgsConstructor
@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    //회원가입
    public SignupRequest createUser(SignupRequest postUserReq) throws BaseException {

        String pwd;
        try {
            // 암호화: postUserReq에서 제공받은 비밀번호를 보안을 위해 암호화시켜 DB에 저장합니다.
            // ex) password123 -> dfhsjfkjdsnj4@!$!@chdsnjfwkenjfnsjfnjsd.fdsfaifsadjfjaf
            pwd = new AES128(Secret.USER_INFO_PASSWORD_KEY).encrypt(postUserReq.getPassword()); // 암호화코드
            postUserReq.setPassword(pwd);
        } catch (Exception ignored) { // 암호화가 실패하였을 경우 에러 발생
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }
        try {
            User user = postUserReq.toEntity();   // DTO -> Entity 변환
            userRepository.save(user);   // 유저 DB에 저장
            return user.toPostUserRes();    // Entity -> DTO 변환
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //아이디중복확인
    public boolean CheckId(String username)throws BaseException{
        boolean duplicate =  false;
        try{
            User chkID = userRepository.findByUsername(username);
            if(chkID == null){
                return false;
            }
            System.out.println("chkID?? "+chkID);
            System.out.println("userId?? "+username);
            if(chkID.getUsername().equals(username)){
                System.out.println("이미 데베에 존재해~!!");
                duplicate =  true;
            }
            return duplicate;
        } catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }


    //전체 유저 가져오는 메서드
    public List<User> getUserList(){

        return userRepository.findAll();
    }

    // 유저 작성하는 메서드
    public User writeUser(User user){
        return userRepository.save(user);
    }

    //유저 아이디를 이용해 User를 리턴받는 메서드
    public User findUserById(int id){
        return userRepository.findById(id);
    }


    //user 객체를 받아 해당 객체와 같은 id의 User를 수정하는 메서드
    public User modifyUser(User newUser){
        User user = userRepository.findById(newUser.getId().intValue());
        if (user == null) {
            return null;
        }
        user.setUsername(newUser.getUsername());
        user.setPassword(newUser.getPassword());
        user.setNickname(newUser.getNickname());
        user.setLastName(newUser.getLastName());
        user.setFirstName(newUser.getFirstName());
        user.setRegionDepth1(newUser.getRegionDepth1());
        user.setRegionDepth2(newUser.getRegionDepth2());
        user.setRegionDepth3(newUser.getRegionDepth3());
        user.setEmail(newUser.getEmail());
        user.setCoin(newUser.getCoin());
        user.setIsAdmin(newUser.getIsAdmin());
        user.setCreatedAt(newUser.getCreatedAt());
        user.setIsDeleted(newUser.getIsDeleted());
        return userRepository.save(user);
    }


    //id를 이용해 user를 삭제하는 메서드
    public void removeUserById(int id){
        User user = userRepository.findById(id);
        if(user!=null)
            userRepository.deleteById(id);
    }
}
