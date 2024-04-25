package com.lemon.backend.domain.characters.dto.response;

import lombok.*;

@Getter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CharactersGetDto {
    String nickname;
    String imageUrl;
    Long characterId;
    Boolean mainCharacter;
}
