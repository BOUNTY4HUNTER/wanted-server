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
   public void getListUser(){
       List<User> userList = userService.getUserList();
       System.out.println("Userlist = " + userList);
       assertEquals(userList.size(), 5);


   }

   @Test
   public void modifyTest(){
       User user = userService.findUserById(1);
       user.setUsername("kinggodbell");
       user.setNickname("godbell");
       userService.modifyUser(user);

       User user2 = userService.findUserById(1);
       assertEquals(user.getNickname(), user2.getNickname());
       assertEquals(user.getUsername(), user2.getUsername());
   }

   @Test
   public void removeTest(){
       int testId = 3;
       assertTrue(userService.findUserById(testId)!=null);
       userService.removeUser(testId);
       assertEquals(userService.findUserById(testId), null);

   }


   @BeforeEach
    public void init(){
       for(int i=1; i<=5; i++) {
           User user = new User();
           user.setId(i);
           user.setUsername("gdsc" + i);
           user.setPassword("asdc" + i);
           user.setNickname("nick" + i);
           user.setLast_name("lastname" + i);
           user.setFirst_name("firstname" + i);
           user.setRegion_1depth("대전");
           user.setRegion_2depth("서구");
           user.setRegion_3depth("도안동");
           user.setEmail("dbswlsdyd@naver.com");
           user.setCoin(10000);
           user.setIs_admin("saff23fdsf2");
           //user.setCreated_at();는 생성시 자동 설정
           user.setIs_deleted(0);
           userService.writeUser(user);
       }
   }



}