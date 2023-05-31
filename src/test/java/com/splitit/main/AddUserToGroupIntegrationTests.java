package com.splitit.main;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.splitit.dto.AddUserToGroupDto;
import com.splitit.dto.CreateGroupRequestDto;
import com.splitit.dto.RegisterUserDto;
import com.splitit.dto.UserLoginRequestDto;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
public class AddUserToGroupIntegrationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private String token;
	private String userName = "testUser";

	@BeforeAll
	public void setup() throws Exception {
		RegisterUserDto user = new RegisterUserDto();
		user.setUserName("testUser");
		user.setPassword("password123");
		user.setEmail("testUser@example.com");
		user.setFirstName("testFirst");
		user.setLastName("testLast");
		user.setPhoneNumber("8978325434");

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

		CreateGroupRequestDto createGroupRequestDto = new CreateGroupRequestDto();
		createGroupRequestDto.setGroupName("test group");
		createGroupRequestDto.setDescription("test description");

		mockMvc.perform(post("/group/create").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + token)
				.content(objectMapper.writeValueAsString(createGroupRequestDto)))
				.andExpect(status().isOk());

		user = new RegisterUserDto();
		user.setUserName("testUser2");
		user.setPassword("password123");
		user.setEmail("testUser2@example.com");
		user.setFirstName("testFirst");
		user.setLastName("testLast");
		user.setPhoneNumber("9704292581");

		mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(user)))
				.andExpect(status().isOk());
	}

	@Test
	public void shouldAllowAddingExistingUserToExistingGroup() throws Exception {
		AddUserToGroupDto addUserToGroupDto = new AddUserToGroupDto();
		addUserToGroupDto.setGroupName("test group");
		addUserToGroupDto.setPhoneNumbers(Arrays.asList("9704292581"));
		mockMvc.perform(post("/group/add").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + token)
				.content(objectMapper.writeValueAsString(addUserToGroupDto)))
				.andExpect(status().isOk());
	}

	@AfterAll
	public void cleanup() throws Exception {
		jdbcTemplate.execute("DELETE FROM users");
		jdbcTemplate.execute("DELETE FROM groups");
		jdbcTemplate.execute("DELETE FROM user_group");
	}

}
