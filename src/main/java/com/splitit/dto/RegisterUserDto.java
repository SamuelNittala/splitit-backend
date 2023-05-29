package com.splitit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterUserDto {

	@JsonProperty("first_name")
	@NotBlank(message = "First name must not be empty")
	@Nonnull
	private String firstName;

	@JsonProperty("last_name")
	@NotBlank(message = "Last name must not be empty")
	@Nonnull
	private String lastName;

	@JsonProperty("user_name")
	@NotBlank(message = "User name must not be empty")
	@Nonnull
	private String userName;

	@NotBlank(message = "Email must not be empty")
	@Nonnull
	@Email(message = "The email is not a valid email")
	private String email;

	@NotBlank(message = "Password must not be empty")
	@Nonnull
	private String password;
}
