package com.lemon.backend.domain.letter.entity;

import com.lemon.backend.domain.base.BaseEntity;
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
@SQLDelete(sql = "UPDATE letter SET is_deleted = TRUE WHERE letter_id = ?")
public class Letter extends BaseEntity {
}
