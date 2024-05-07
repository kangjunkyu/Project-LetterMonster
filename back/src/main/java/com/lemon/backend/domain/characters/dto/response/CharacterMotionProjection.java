package com.lemon.backend.domain.characters.dto.response;

import lombok.*;

@Getter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CharacterMotionProjection {
    Long id;
    String url;
    String name;
    String CharacterNickname;
}
