package com.lemon.backend.domain.friend.controller;

import com.lemon.backend.domain.friend.dto.response.GroupResponseDto;
import com.lemon.backend.domain.friend.service.FriendsService;
import com.lemon.backend.domain.friend.service.GroupsService;
import com.lemon.backend.domain.users.user.service.UserService;
import com.lemon.backend.global.exception.CustomException;
import com.lemon.backend.global.exception.ErrorCode;
import com.lemon.backend.global.response.SuccessCode;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

import static com.lemon.backend.global.response.CommonResponseEntity.getResponseEntity;

@Tag(name = "Friend 컨트롤러", description = "Friend Controller API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/friends")
public class FriendsController {

    private final FriendsService friendsService;

    @PostMapping("/{friendId}")
    public ResponseEntity<?> addFriend(Authentication authentication, @PathVariable(value = "friendId") Integer friendId) {
        Integer loginId = (Integer) authentication.getPrincipal();

        String addFriendNickname = friendsService.addFriend(loginId, friendId);

        return getResponseEntity(SuccessCode.CREATED, addFriendNickname);
    }

    @DeleteMapping("/{friendId}")
    public ResponseEntity<?> deleteFriend(Authentication authentication, @PathVariable Integer friendId) {
        if (authentication.getPrincipal() instanceof Integer) {
            Integer loginId = (Integer) authentication.getPrincipal();
            friendsService.deleteFriend(loginId, friendId);
            return getResponseEntity(SuccessCode.OK);
        } else {
            throw new CustomException(ErrorCode.INVALID_ACCESS);
        }
    }

    @PutMapping("/{friendId}/groups")
    public ResponseEntity<?> changeGroup(@PathVariable Long friendId, @RequestParam(value = "newGroupId") Long newGroupId) {
        friendsService.changeFriendGroup(friendId, newGroupId);
        return getResponseEntity(SuccessCode.OK);
    }

}
