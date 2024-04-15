package com.lemon.backend.domain.sketchbook.dto.requestDto;

import com.lemon.backend.domain.users.entity.Users;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SketchbookCreateDto {

//    private Long id;
//    private Boolean isPublic;
//    private String shareLink;
    private String name;
    private Integer userId;

}
