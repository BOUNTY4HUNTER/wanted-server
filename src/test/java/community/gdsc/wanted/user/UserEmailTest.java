package community.gdsc.wanted.user;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import community.gdsc.wanted.domain.User;
import community.gdsc.wanted.dto.SigninRequestDTO;
import community.gdsc.wanted.dto.SignupRequestDTO;
import community.gdsc.wanted.repository.UserRepository;

@SpringBootTest
public class UserEmailTest {
	@Autowired
	private WebApplicationContext context;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	private MockMvc mvc;

	@BeforeEach
	public void setup() {
		mvc = MockMvcBuilders
			.webAppContextSetup(context)
			.build();
	}

	@BeforeEach
	@AfterEach
	public void resetDatabase() {
		userRepository.deleteAll();
	}

	@Test
	void findId() throws Exception {
		String email = userRepository.save(
			User.builder()
				.username("12191632")
				.password(passwordEncoder.encode("testtest"))
				.firstName("yun")
				.lastName("jinyong")
				.email("12191632Y@inha.edu")
				.nickname("jjingyyong")
				.regionDepth1("대전광역시")
				.regionDepth2("서구")
				.regionDepth3("도안동")
				.build()
		).getEmail();

		mvc.perform(
				get("/api/user/login/forgot/id")
					.queryParam("email",email)
			)
			.andDo(MockMvcResultHandlers.print())
			.andExpect(status().isOk());
	}


	@Test
	void findPassword() throws Exception {
		String username = userRepository.save(
			User.builder()
				.username("12191632")
				.password(passwordEncoder.encode("test1234"))
				.firstName("yun")
				.lastName("jinyong")
				.email("12191632Y@inha.edu")
				.nickname("asdf")
				.regionDepth1("대전광역시")
				.regionDepth2("서구")
				.regionDepth3("도안동")
				.build()
		).getUsername();

		mvc.perform(
				get("/api/user/login/forgot/password")
					.queryParam("id",username)
			)
			.andDo(MockMvcResultHandlers.print())
			.andExpect(status().isOk());
	}

}
