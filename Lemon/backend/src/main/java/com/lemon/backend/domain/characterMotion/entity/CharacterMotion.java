package com.lemon.backend.domain.characterMotion.entity;

import com.lemon.backend.domain.base.BaseEntity;
import com.lemon.backend.domain.character.entity.Characters;
import com.lemon.backend.domain.motion.entity.Motion;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE character_motion SET is_deleted = TRUE WHERE character_motion_id = ?")
public class CharacterMotion extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "character_motion_id")
    private Long id;

    @Column(name = "image_url", length = 1000)
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "characters_id", nullable = true)
    private Characters characters;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "motion_id", nullable = true)
    private Motion motion;

}
