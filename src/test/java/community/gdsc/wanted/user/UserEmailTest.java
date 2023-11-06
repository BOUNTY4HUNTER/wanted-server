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
	void signup() throws Exception {
		SignupRequestDTO dto = new SignupRequestDTO(
			"testtest",
			"testtest",
			"jongha",
			"kim",
			"12191579@inha.edu",
			"asdf",
			"인천광역시",
			"서구",
			"마전동"
		);

		String body = new ObjectMapper().writeValueAsString(
			dto
		);

		mvc.perform(
				post("/api/user")
					.content(body)
					.contentType(MediaType.APPLICATION_JSON)
					.characterEncoding("utf-8")
			)
			.andDo(MockMvcResultHandlers.print())
			.andExpect(status().isOk())
			.andReturn();

		Assertions.assertEquals(
			userRepository.findByUsername("testtest"),
			dto.toEntity()
		);
	}

	@Test
	void signupFailByIllegalArgs() throws Exception {
		SignupRequestDTO dto = new SignupRequestDTO(
			"testtest",
			"tes",
			"jongha",
			"kim",
			"12191579@inha.edu",
			"asdf",
			"인천광역시",
			"서구",
			"마전동"
		);

		String body = new ObjectMapper().writeValueAsString(
			dto
		);

		mvc.perform(
				post("/api/user")
					.content(body)
					.contentType(MediaType.APPLICATION_JSON)
					.characterEncoding("utf-8")
			)
			.andDo(MockMvcResultHandlers.print())
			.andExpect(status().isBadRequest())
			.andReturn();
	}

	@Test
	void getUserById() throws Exception {
		int id = userRepository.save(
			User.builder()
				.username("testtest")
				.password("testtest")
				.firstName("kim")
				.lastName("jongha")
				.email("12191579@inha.edu")
				.nickname("asdf")
				.regionDepth1("인천광역시")
				.regionDepth2("서구")
				.regionDepth3("마전동")
				.build()
		).getId();

		mvc.perform(get("/api/user/" + id))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.username").value("testtest"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("kim"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("jongha"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.email").value("12191579@inha.edu"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.nickname").value("asdf"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.regionDepth1").value("인천광역시"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.regionDepth2").value("서구"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.regionDepth3").value("마전동"))
			.andReturn();
	}

	@Test
	void login() throws Exception {
		userRepository.save(
			User.builder()
				.username("testtest")
				.password(passwordEncoder.encode("testtest"))
				.firstName("kim")
				.lastName("jongha")
				.email("12191579@inha.edu")
				.nickname("asdf")
				.regionDepth1("인천광역시")
				.regionDepth2("서구")
				.regionDepth3("마전동")
				.build()
		);

		String body = new ObjectMapper().writeValueAsString(
			new SigninRequestDTO("testtest", "testtest")
		);

		mvc.perform(
				post("/api/user/signin")
					.content(body)
					.characterEncoding("utf-8")
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(MockMvcResultHandlers.print())
			.andExpect(status().isOk());
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
				get("/api/user/login/forgot/id/")
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
				get("/api/user/login/forgot/password/")
					.queryParam("id",username)
			)
			.andDo(MockMvcResultHandlers.print())
			.andExpect(status().isOk());
	}

}
