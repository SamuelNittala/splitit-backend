package com.splitit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponseDto {
	private String firstName;
	private String lastName;
	private String userName;
	private String email;
	private String phoneNumber;
}
