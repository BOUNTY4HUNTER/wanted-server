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
        User user = userRepository.findById(newUser.getId());
        if(user==null)return null;
        user.setUsername(newUser.getUsername());
        user.setPassword(newUser.getPassword());
        user.setNickname(newUser.getNickname());
        user.setLast_name(newUser.getLast_name());
        user.setFirst_name(newUser.getFirst_name());
        user.setRegion_1depth(newUser.getRegion_1depth());
        user.setRegion_2depth(newUser.getRegion_2depth());
        user.setRegion_3depth(newUser.getRegion_3depth());
        user.setEmail(newUser.getEmail());
        user.setCoin(newUser.getCoin());
        user.setIs_admin(newUser.getIs_admin());
        user.setCreated_at(newUser.getCreated_at());
        user.setIs_deleted(newUser.getIs_deleted());
        return userRepository.save(user);
    }


    //id를 이용해 user를 삭제하는 메서드
    public void removeUser(int id){
        User user = userRepository.findById(id); //아니 orElse가 왜 안붙어
        if(user!=null)
            userRepository.deleteById(id);
    }






}
