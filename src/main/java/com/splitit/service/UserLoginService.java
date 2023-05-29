package com.splitit.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.splitit.config.UserLoginDetails;
import com.splitit.entity.User;
import com.splitit.repo.UserRepository;

@Component
public class UserLoginService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Optional<User> userInfo = userRepository.findByUserName(userName);
		return userInfo.map(UserLoginDetails::new)
				.orElseThrow(() -> new UsernameNotFoundException("user not found " + userName));
	}
}
