package com.splitit.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.splitit.dto.CreateGroupRequestDto;
import com.splitit.entity.Group;
import com.splitit.service.GroupService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/group")
public class GroupController {

	@Autowired
	private GroupService groupService;

	@PostMapping("/create")
	public ResponseEntity<?> addGroup(@Valid @RequestBody CreateGroupRequestDto addGroupRequestDto) {
		Group group = groupService.createGroup(addGroupRequestDto);
		Map<String, String> response = new HashMap<>();
		response.put("success", "Group " + group.getName() + " created");
		return ResponseEntity.ok(response);

	}

}
