package com.splitit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.splitit.entity.UserGroup;

public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {
}
