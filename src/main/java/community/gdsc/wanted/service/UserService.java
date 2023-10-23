package community.gdsc.wanted.service;

import community.gdsc.wanted.domain.User;
import community.gdsc.wanted.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Transactional
@RequiredArgsConstructor
@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

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
