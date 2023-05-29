package com.splitit.main;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.splitit.dto.CreateGroupRequestDto;
import com.splitit.dto.RegisterUserDto;
import com.splitit.dto.UserLoginRequestDto;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
public class GroupCreationIntegrationTests {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	private String token;

	@BeforeAll
	public void setup() throws Exception {
		RegisterUserDto user = new RegisterUserDto();
		user.setUserName("testUser");
		user.setPassword("password123");
		user.setEmail("testUser@example.com");
		user.setFirstName("testFirst");
		user.setLastName("testLast");

		mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(user)))
				.andExpect(status().isOk());

		UserLoginRequestDto userLoginRequestDto = new UserLoginRequestDto();
		userLoginRequestDto.setUserName("testUser");
		userLoginRequestDto.setPassword("password123");

		MvcResult loginResult = mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(userLoginRequestDto)))
				.andExpect(status().isOk())
				.andReturn();

		String loginResponse = loginResult.getResponse()
				.getContentAsString();

		String token = JsonPath.parse(loginResponse)
				.read("$.token");

		this.token = token;
	}

	@Test
	public void shouldCreateNewGroup() throws Exception {

//		RegisterUserDto user = new RegisterUserDto();
//		user.setUserName("testUser");
//		user.setPassword("password123");
//		user.setEmail("testUser@example.com");
//		user.setFirstName("testFirst");
//		user.setLastName("testLast");
//
//		mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON)
//				.content(objectMapper.writeValueAsString(user)))
//				.andExpect(status().isOk());
//
//		UserLoginRequestDto userLoginRequestDto = new UserLoginRequestDto();
//		userLoginRequestDto.setUserName("testUser");
//		userLoginRequestDto.setPassword("password123");
//
//		MvcResult loginResult = mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
//				.content(objectMapper.writeValueAsString(userLoginRequestDto)))
//				.andExpect(status().isOk())
//				.andReturn();
//
//		String loginResponse = loginResult.getResponse()
//				.getContentAsString();
//
//		String token = JsonPath.parse(loginResponse)
//				.read("$.token");

		CreateGroupRequestDto createGroupRequestDto = new CreateGroupRequestDto();
		createGroupRequestDto.setGroupName("test group");
		createGroupRequestDto.setDescription("test description");

		mockMvc.perform(post("/group/create").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + token)
				.content(objectMapper.writeValueAsString(createGroupRequestDto)))
				.andExpect(status().isOk());
	}

}
