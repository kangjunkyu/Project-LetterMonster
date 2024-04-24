package com.lemon.backend.domain.friend.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter(AccessLevel.PACKAGE)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupResponseDto {
    private Long id;
    private String name;
    private List<FriendResponseDto> friendList;
}
