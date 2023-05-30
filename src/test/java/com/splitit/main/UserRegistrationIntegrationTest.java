package com.splitit.main;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.splitit.dto.RegisterUserDto;
import com.splitit.repo.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
public class UserRegistrationIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private UserRepository userRepository;

	private String userName = "testUser";

	@Test
	public void shouldCreateNewUser() throws Exception {
		RegisterUserDto user = new RegisterUserDto();
		user.setUserName(userName);
		user.setPassword("password123");
		user.setEmail("testUser@example.com");
		user.setFirstName("testFirst");
		user.setLastName("testLast");
		user.setPhoneNumber("111111111");

		mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(user)))
				.andExpect(status().isOk());
	}

	@Test
	public void shouldThrowErrorsWhenKeyNotPresent() throws Exception {
		RegisterUserDto user = new RegisterUserDto();
		user.setUserName("testUser");
		user.setPassword("password123");
		user.setEmail("testUser@example.com");
		user.setPhoneNumber("111111111");

		mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(user)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors").isArray());
	}

	@AfterAll
	public void cleanUp() throws Exception {
		userRepository.deleteById(userRepository.findByUserName(userName)
				.get()
				.getId());
	}

}
