package com.splitit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.splitit.dto.RegisterResponseDto;
import com.splitit.dto.RegisterUserDto;
import com.splitit.dto.UserLoginRequestDto;
import com.splitit.dto.UserLoginResponseDto;
import com.splitit.entity.User;
import com.splitit.exception.UserAlreadyExistsException;
import com.splitit.service.JwtService;
import com.splitit.service.UserService;

import jakarta.validation.Valid;

@RestController
public class UserController {
	@Autowired
	private UserService userService;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/register")
	public ResponseEntity<RegisterResponseDto> register(@Valid @RequestBody RegisterUserDto registerUserDto) {
		User user = userService.register(registerUserDto);

		RegisterResponseDto userResponseDto = new RegisterResponseDto();
		userResponseDto.setFirstName(user.getFirstName());
		userResponseDto.setLastName(user.getLastName());

		return ResponseEntity.ok(userResponseDto);
	}

	@PostMapping("/login")
	public ResponseEntity<?> authenticateAndGetToken(@RequestBody UserLoginRequestDto authRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
		if (authentication.isAuthenticated()) {
			UserLoginResponseDto userLoginResponseDto = new UserLoginResponseDto();
			userLoginResponseDto.setToken(jwtService.generateToken(authRequest.getUserName()));
			return ResponseEntity.ok(userLoginResponseDto);
		} else {
			throw new UsernameNotFoundException("invalid user request !");
		}

	}

	@ExceptionHandler(UserAlreadyExistsException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public String handleUserAlreadyExistsException(UserAlreadyExistsException e) {
		return e.getMessage();
	}

	@ExceptionHandler(UsernameNotFoundException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public String handleUserNotFoundException(UsernameNotFoundException e) {
		return e.getMessage();
	}
}
