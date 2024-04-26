package com.lemon.backend.domain.characters.dto.response;

import lombok.*;

@Getter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SelectCharacterMotionDto {
    String imageUrl;
    Long CharacterMotionId;
}
