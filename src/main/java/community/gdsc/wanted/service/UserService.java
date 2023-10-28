package community.gdsc.wanted.service;

import static community.gdsc.wanted.Base.BaseResponseStatus.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import community.gdsc.wanted.Base.BaseException;
import community.gdsc.wanted.RequestModel.SignupRequest;
import community.gdsc.wanted.domain.User;
import community.gdsc.wanted.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class UserService {
	@Autowired
	UserRepository userRepository;
	
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

		if (newPassword != null) {
			user.setPassword(newPassword);
		}

		if (newNickname != null) {
			user.setNickname(newNickname);
		}

		if (newRegionDepth1 != null) {
			user.setRegionDepth1(newRegionDepth1);
		}

		if (newRegionDepth2 != null) {
			user.setRegionDepth2(newRegionDepth2);
		}

		if (newRegionDepth3 != null) {
			user.setRegionDepth3(newRegionDepth3);
		}

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
}
