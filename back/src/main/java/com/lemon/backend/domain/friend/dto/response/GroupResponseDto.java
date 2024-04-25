package com.lemon.backend.domain.friend.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupResponseDto {

    private Long id;

    public GroupResponseDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    private String name;
    private List<FriendResponseDto> friendList;

    public GroupResponseDto(String name) {
        this.name = name;
    }
}
