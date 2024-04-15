package com.lemon.backend.domain.character.entity;

import com.lemon.backend.domain.base.BaseEntity;
import com.lemon.backend.domain.characterMotion.entity.CharacterMotion;
import com.lemon.backend.domain.letter.entity.Letter;
import com.lemon.backend.domain.users.entity.Users;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
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
@SQLDelete(sql = "UPDATE characters SET is_deleted = TRUE WHERE characters_id = ?")
public class Characters extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "characters_id")
    private Long id;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "main_character")
    @ColumnDefault("false")
    private Boolean mainCharacter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", nullable = true)
    private Users users;

    @OneToMany(mappedBy = "characters", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private List<CharacterMotion> characterMotionList = new ArrayList<>();

    @OneToMany(mappedBy = "characters", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private List<Letter> letterList = new ArrayList<>();
}
