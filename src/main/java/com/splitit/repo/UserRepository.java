package com.splitit.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.splitit.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUserName(String userName);

	Optional<User> findByEmail(String email);

	Optional<User> findByPhoneNumber(String string);
}
