package com.lemon.backend.domain.FavoriteSketchbook.dto;

import com.lemon.backend.domain.sketchbook.dto.responseDto.SketchbookGetFromFavoriteDto;
import com.lemon.backend.domain.sketchbook.entity.Sketchbook;
import com.lemon.backend.domain.users.user.entity.Users;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FavoriteSketchbookGetDto {
//    private Users user;
    private SketchbookGetFromFavoriteDto sketchbook;
}
