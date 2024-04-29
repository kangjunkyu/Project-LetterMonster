package com.lemon.backend.domain.friend.dto.response;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupResponseDto {

    private Long id;
    private String name;
    private List<FriendResponseDto> friendList = new ArrayList<>();

    public GroupResponseDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public GroupResponseDto(String name) {
        this.name = name;
    }
}
