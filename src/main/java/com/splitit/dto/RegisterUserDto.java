package com.splitit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.splitit.util.ApplicationConstants;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterUserDto {

	@JsonProperty("first_name")
	@NotBlank(message = ApplicationConstants.ERROR_FIRST_NAME_EMPTY)
	@Nonnull
	private String firstName;

	@JsonProperty("last_name")
	@NotBlank(message = ApplicationConstants.ERROR_LAST_NAME_EMPTY)
	@Nonnull
	private String lastName;

	@JsonProperty("user_name")
	@NotBlank(message = ApplicationConstants.ERROR_USER_NAME_EMPTY)
	@Nonnull
	private String userName;

	@NotBlank(message = ApplicationConstants.ERROR_EMAIL_EMPTY)
	@Nonnull
	@Email(message = ApplicationConstants.INVALID_EMAIL)
	private String email;

	@NotBlank(message = ApplicationConstants.ERROR_PASSWORD_EMPTY)
	@Nonnull
	private String password;

	@NotBlank(message = ApplicationConstants.ERROR_PHONENUMBER_EMPTY)
	@Nonnull
	private Integer phoneNumber;
}
