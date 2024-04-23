package com.lemon.backend.domain.characters.entity;

import com.lemon.backend.domain.base.BaseEntity;
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

    @Column(name = "image_url", columnDefinition = "TEXT")
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "characters_id", nullable = true)
    private Characters characters;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "motion_id", nullable = true)
    private Motion motion;

    // 양방향 연관관계 편의 메서드
    // 이거는 일대다로 양방향을 맺어 놓은 상태일 때, N의 엔티티를 생성할 때 리스트로 관리하는 1쪽에 객체를 추가하는 편의 메서드임.
    public void addCharacterMotion(Characters characters) {
        this.characters = characters;
        characters.getCharacterMotionList().add(this);

    }
    // 빌더패턴으로 객체 생성시 양방향 연관관계 편의 메서드 호출
    @Builder(toBuilder = true)
    CharacterMotion(Characters characters, Motion motion, String url) {
        this.url = url;
        this.motion =  motion;
        this.characters = characters;
        addCharacterMotion(characters);
    }

}
/* 사용 예시
CharacterMotion cm = Builder.characters().motion().url().build();

cm 빌더로 생성시 이런식으로 하면, Characters의 ChracterMotionList에 CharacterMotion cm이 자동으로 추가됨
 */