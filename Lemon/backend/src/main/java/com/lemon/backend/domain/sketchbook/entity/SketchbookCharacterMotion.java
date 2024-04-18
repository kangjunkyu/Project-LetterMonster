package com.lemon.backend.domain.sketchbook.entity;

import com.lemon.backend.domain.base.BaseEntity;
import com.lemon.backend.domain.characters.entity.CharacterMotion;
import com.lemon.backend.domain.characters.entity.Characters;
import com.lemon.backend.domain.letter.entity.Letter;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE sketchbookcharactermotion SET is_deleted = TRUE WHERE sketchbookcharactermotion_id = ?")
public class SketchbookCharacterMotion extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sketchbookcharactermotion_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sketchbook_id", nullable = true)
    private Sketchbook sketchbook;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "charactermotion_id", nullable = true)
    private CharacterMotion characterMotion;

    @OneToMany(mappedBy = "sketchbookCharacterMotion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Letter> letters;

    public SketchbookCharacterMotion(Long id, Sketchbook sketchbook, CharacterMotion characterMotion) {
        this.id = id;
        this.sketchbook = sketchbook;
        this.characterMotion = characterMotion;
    }

}
