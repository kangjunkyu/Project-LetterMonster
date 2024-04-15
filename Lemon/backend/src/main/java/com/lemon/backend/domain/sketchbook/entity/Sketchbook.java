package com.lemon.backend.domain.sketchbook.entity;

import jakarta.persistence.Entity;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE sketchbook SET is_deleted = TRUE WHERE sketchbook_id = ?")
public class Sketchbook {
}
