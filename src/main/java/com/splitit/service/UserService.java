package com.splitit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.splitit.dto.RegisterUserDto;
import com.splitit.entity.User;
import com.splitit.exception.UserAlreadyExistsException;
import com.splitit.repo.UserRepository;
import com.splitit.util.ApplicationConstants;

@Service
public class UserService {

	private final UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User register(RegisterUserDto registerUserDto) {
		if (userRepository.findByEmail(registerUserDto.getEmail()).isPresent()) {
			throw new UserAlreadyExistsException(registerUserDto.getEmail() + ApplicationConstants.EMAIL_ALREADY_USED);
		}

		if (userRepository.findByPhoneNumber(registerUserDto.getPhoneNumber()).isPresent()) {
			throw new UserAlreadyExistsException(
					registerUserDto.getPhoneNumber() + ApplicationConstants.PHONENUMBER_ALREADY_USED);
		}

		if (userRepository.findByUserName(registerUserDto.getUserName()).isPresent()) {
			throw new UserAlreadyExistsException(
					registerUserDto.getUserName() + ApplicationConstants.USERNAME_ALREADT_USED);
		}

		User user = new User();
		user.setUserName(registerUserDto.getUserName());
		user.setFirstName(registerUserDto.getFirstName());
		user.setLastName(registerUserDto.getLastName());
		user.setEmail(registerUserDto.getEmail());
		user.setPhoneNumber(registerUserDto.getPhoneNumber());
		user.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));
		userRepository.save(user);

		return user;
	}

}
