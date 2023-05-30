package com.splitit.repo;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.splitit.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {

	Optional<User> findByUserName(String userName);

	Optional<User> findByEmail(String email);

	Optional<User> findByPhoneNumber(Integer integer);
}
