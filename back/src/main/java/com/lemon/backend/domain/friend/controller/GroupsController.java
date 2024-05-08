package com.lemon.backend.domain.friend.controller;

import com.lemon.backend.domain.friend.dto.response.GroupResponseDto;
import com.lemon.backend.domain.friend.service.FriendsService;
import com.lemon.backend.domain.friend.service.GroupsService;
import com.lemon.backend.domain.users.user.service.UserService;
import com.lemon.backend.global.response.SuccessCode;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.lemon.backend.global.response.CommonResponseEntity.getResponseEntity;

@Tag(name = "Group 컨트롤러", description = "Group Controller API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/groups")
public class GroupsController {
    private final GroupsService groupsService;


    @GetMapping("/all")
    public ResponseEntity<?> getAllGroup(Authentication authentication) {
        Integer loginId = (Integer) authentication.getPrincipal();

        List<GroupResponseDto> group = groupsService.getGroupForUser(loginId);
        return getResponseEntity(SuccessCode.OK, group);
    }
    @PutMapping("/{groupId}")
    public ResponseEntity<?> updateGroup(@PathVariable Long groupId, @RequestParam(value = "newName") String newName) {
        Long updateGroupId = groupsService.changeGroupName(groupId, newName);
        return getResponseEntity(SuccessCode.CREATED, updateGroupId);
    }

    @DeleteMapping("/{groupId}")
    public ResponseEntity<?> deleteGroup(Authentication authentication, @PathVariable Long groupId) {
        Integer loginId = (Integer) authentication.getPrincipal();
        groupsService.deleteGroup(loginId, groupId);
        return getResponseEntity(SuccessCode.OK);
    }

    @PostMapping
    public ResponseEntity<?> createGroup(Authentication authentication, @RequestParam(value = "groupName") String groupName) {
        Integer loginId = (Integer) authentication.getPrincipal();

        Long createGroupId = groupsService.createGroup(loginId, groupName);

        return getResponseEntity(SuccessCode.CREATED, createGroupId);
    }
}
