package com.splitit.dto;

import java.util.List;

import com.splitit.entity.Group;
import com.splitit.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserGroupDto {

	private String groupName;

	private List<String> phoneNumbers;

}
