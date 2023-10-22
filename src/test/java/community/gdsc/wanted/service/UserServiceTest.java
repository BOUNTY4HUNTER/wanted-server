package community.gdsc.wanted.service;

import community.gdsc.wanted.domain.User;
import community.gdsc.wanted.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class UserServiceTest {
   @Autowired
    UserService userService;

   @Autowired
    UserRepository userRepository;


   @Test
   public void getListUsers(){
       List<User> userList = userService.getUserList();
       System.out.println("Userlist = " + userList);
       assertEquals(userList.size(), 5);


   }

   @Test
   public void modifyUser(){
       User user = userService.findUserById(1);
       user.setUsername("kinggodbell");
       user.setNickname("godbell");
       userService.modifyUser(user);

       User user2 = userService.findUserById(1);
       assertEquals(user.getNickname(), user2.getNickname());
       assertEquals(user.getUsername(), user2.getUsername());
   }

   @Test
   public void removeUser(){
       int testId = 3;
       assertTrue(userService.findUserById(testId)!=null);
       userService.removeUserById(testId);
       assertEquals(userService.findUserById(testId), null);

   }


   @BeforeEach
    public void initUsers(){
       for(int i=1; i<=5; i++) {
           User user = new User();
           user.setId(i);
           user.setUsername("gdsc" + i);
           user.setPassword("asdc" + i);
           user.setNickname("nick" + i);
           user.setLastName("lastname" + i);
           user.setFirstName("firstname" + i);
           user.setRegionDepth1("대전");
           user.setRegionDepth2("서구");
           user.setRegionDepth3("도안동");
           user.setEmail("dbswlsdyd@naver.com");
           user.setCoin(10000);
           user.setIsAdmin("saff23fdsf2");
           //user.setCreated_at();는 생성시 자동 설정
           user.setIsDeleted(0);
           userService.writeUser(user);
       }
   }
}