package community.gdsc.wanted.service;

import static community.gdsc.wanted.Base.BaseResponseStatus.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import community.gdsc.wanted.Base.BaseException;
import community.gdsc.wanted.RequestModel.SignupRequest;
import community.gdsc.wanted.domain.User;
import community.gdsc.wanted.repository.UserRepository;
import community.gdsc.wanted.utils.MailHandler;
import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class UserService {
	@Autowired
	UserRepository userRepository;

	//메일전송
	@Autowired
	private final JavaMailSender javaMailSender;
	//@Value("${spring.mail.username}")
	private String from = "dbswlsdyd730@naver.com";

	//회원가입
	public SignupRequest
	createUser(SignupRequest postUserReq) throws BaseException {
		String pwd;
		try {
			pwd = postUserReq.getPassword();
			postUserReq.setPassword(pwd);
			User user = postUserReq.toEntity();   // DTO -> Entity 변환
			userRepository.save(user);   // 유저 DB에 저장
			return user.toPostUserRes();    // Entity -> DTO 변환
		} catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
			throw new BaseException(DATABASE_ERROR);
		}
	}

	//아이디중복확인
	public boolean CheckId(String username) throws BaseException {
		try {
			User chkID = userRepository.findByUsername(username);
			if (chkID == null) {
				return false;
			}
			System.out.println("chkID?? " + chkID);
			System.out.println("userId?? " + username);
			if (chkID.getUsername().equals(username)) {
				System.out.println("이미 데베에 존재해~!!");
				return false;
			}
			return true;
		} catch (Exception e) {
			throw new BaseException(DATABASE_ERROR);
		}
	}

	//전체 유저 가져오는 메서드
	public List<User> getUserList() {

		return userRepository.findAll();
	}

	// 유저 작성하는 메서드
	public User addUser(User user) {
		return userRepository.save(user);
	}

	//유저 아이디를 이용해 User를 리턴받는 메서드
	public User findUserById(int id) {
		return userRepository.findById(id);
	}

	//유저 nickname을 통해 User를 리턴받는 메서드
	public User findUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	//user 객체를 받아 해당 객체와 같은 id의 User를 수정하는 메서드
	public User modifyUser(User newUser, String newUserURL, String newPassword, String newNickname,
		String newRegionDepth1,
		String newRegionDepth2, String newRegionDepth3) {

		User user = userRepository.findById(newUser.getId().intValue());
		if (user == null) {
			return null;
		}

		//TODO : profile 업데이트 넣기

		//baseuser.set
		user.setPassword(newPassword);
		user.setNickname(newNickname);
		user.setRegionDepth1(newRegionDepth1);
		user.setRegionDepth2(newRegionDepth2);
		user.setRegionDepth3(newRegionDepth3);
		return userRepository.save(user);
	}

	//id를 이용해 user를 삭제하는 메서드
	public void removeUserById(int id) {
		User user = userRepository.findById(id);
		if (user == null)
			return;
		userRepository.deleteById(id);

		// TODO: 예외 throw
	}

	public void findUserId(String email) throws BaseException {
		String name;
		Integer userId;
		try {
			//해당 이메일을 가진 userEntity찾기
			try {
				User user = userRepository.findByEmail(email);
				System.out.println("-------------해당이메일이 있느냐?------------" + user);
				//userEntity에서 name과 userId 추출 후 해당이메일로 보내기
				name = user.getUsername();
				userId = user.getId();
			} catch (Exception e) {
				throw new BaseException(INVALID_EMAIL);
			}

			System.out.println("---------name & userId--------" + name + "     " + userId);

			//이메일보내기
			MailHandler mailHandler = new MailHandler(javaMailSender);
			mailHandler.setFrom(from); //이거 naver메일 보낼때는 필수다!!!!!!
			mailHandler.setTo(email);
			mailHandler.setSubject("[Wanted] " + name + "님의 아이디를 보내드립니다.");
			mailHandler.setText(name + "님의 아이디: " + userId);
			mailHandler.send();

			System.out.println("-------메일 발송이 되었느냐??--------");
		} catch (Exception e) {
			throw new BaseException(DATABASE_ERROR);
		}
	}

	//랜덤함수로 임시비밀번호 구문 만들기
	public String getTempPassword() {
		char[] charSet = new char[] {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
			'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

		String str = "";

		// 문자 배열 길이의 값을 랜덤으로 10개를 뽑아 구문을 작성함
		int idx = 0;
		for (int i = 0; i < 10; i++) {
			idx = (int)(charSet.length * Math.random());
			str += charSet[idx];
		}
		return str;
	}

	//비밀번호 찾기
	public void findPassword(String username) throws BaseException {
		try {
			User user = userRepository.findByUsername(username);

			String name = user.getUsername();

			String tmpPassword = getTempPassword();

			String email = user.getEmail();

			user.setPassword(tmpPassword);

			userRepository.save(user);

			//해당 아이디가 존재하면 해당 이메일로 메일보내기
			MailHandler mailHandler = new MailHandler(javaMailSender);
			mailHandler.setTo(email);
			mailHandler.setSubject("[Wanted] " + name + "님의 임시비밀번호 안내 이메일입니다.");
			mailHandler.setText(name + "님, 안녕하세요. Wanted 임시비밀번호 안내 관련 이메일 입니다." + " 회원님의 임시 비밀번호는 "
				+ tmpPassword + " 입니다." + "로그인 후에 비밀번호를 변경을 해주세요");
			mailHandler.send();
		} catch (Exception exception) {
			throw new BaseException(INVALID_USER_ID);
		}
	}
}
