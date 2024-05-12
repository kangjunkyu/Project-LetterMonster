package com.lemon.backend.domain.FavoriteSketchbook.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FavoriteSketchbookGetOneDto {
    private Boolean isFavorite;
}
