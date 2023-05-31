package com.splitit.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.splitit.dto.AddUserToGroupDto;
import com.splitit.dto.CreateGroupRequestDto;
import com.splitit.entity.Group;
import com.splitit.entity.User;
import com.splitit.entity.UserGroup;
import com.splitit.repo.GroupRepository;
import com.splitit.repo.UserGroupRepository;
import com.splitit.repo.UserRepository;

@Service
public class GroupService {

	@Autowired
	private GroupRepository groupRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserGroupRepository userGroupRepository;

	public Group createGroup(CreateGroupRequestDto createGroupRequestDto) {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		String currentPrincipalName = authentication.getName();
		Optional<User> userOptional = userRepository.findByUserName(currentPrincipalName);
		if (userOptional.isPresent()) {

			Group group = new Group();
			group.setCreator(userOptional.get());
			group.setName(createGroupRequestDto.getGroupName());
			group.setDescription(createGroupRequestDto.getDescription());
			groupRepository.save(group);

			UserGroup userGroup = new UserGroup();
			userGroup.setGroup(group);
			userGroup.setUser(userOptional.get());
			userGroup.setJoinedTime(new Date());
			userGroupRepository.save(userGroup);

			return group;
		}
		throw new UsernameNotFoundException("Invalid reequest!");
	}

	public void addMembersInGroup(AddUserToGroupDto userGroupDto) {

		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		String currentPrincipalName = authentication.getName();
		Optional<User> userOptional = userRepository.findByUserName(currentPrincipalName);

		Long currentPrincipleId = userOptional.get()
				.getId();

		Boolean groupExists = groupRepository
				.getGroupByNameAndCreatorId(userGroupDto.getGroupName(), currentPrincipleId)
				.isPresent();

		if (userOptional.isPresent() && groupExists) {
			for (int i = 0; i < userGroupDto.getPhoneNumbers()
					.size(); i++) {
				UserGroup userGroupForList = new UserGroup();
				if (userRepository.findByPhoneNumber(userGroupDto.getPhoneNumbers()
						.get(i))
						.isPresent()) {
					userGroupForList.setUser(userRepository.findByPhoneNumber(userGroupDto.getPhoneNumbers()
							.get(i))
							.get());
					userGroupForList.setGroup(
							groupRepository.getGroupByNameAndCreatorId(userGroupDto.getGroupName(), currentPrincipleId)
									.get());
				} else {
					throw new UsernameNotFoundException("Invalid reequest!");
				}
			}
		}

	}

}
