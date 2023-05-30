package com.splitit.repo;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.splitit.entity.Group;

public interface GroupRepository extends CrudRepository<Group, Long> {

	Optional<Group> findByGroupName(String groupName);

	Optional<Group> findByName(String groupName);

	

}
