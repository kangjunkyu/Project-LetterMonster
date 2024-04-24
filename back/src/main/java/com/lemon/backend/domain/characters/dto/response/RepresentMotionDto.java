package com.lemon.backend.domain.characters.dto.response;

import lombok.*;

@Getter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RepresentMotionDto {
    String name;
    Long motionId;
    String imageUrl;
}
