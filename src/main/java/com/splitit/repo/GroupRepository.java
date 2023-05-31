package com.splitit.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.splitit.entity.Group;

public interface GroupRepository extends CrudRepository<Group, Long> {

	Optional<Group> findByGroupName(String groupName);

	Optional<Group> findByName(String groupName);

	@Query(value = "SELECT * FROM groups where name = ?1 AND id = ?2", nativeQuery = true)
	Optional<Group> getGroupByNameAndCreatorId(String groupName, Long id);

}
