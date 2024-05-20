package com.lemon.backend.domain.letter.entity;

import com.lemon.backend.domain.base.BaseEntity;
import com.lemon.backend.domain.characters.entity.Characters;
import com.lemon.backend.domain.sketchbook.entity.Sketchbook;
import com.lemon.backend.domain.sketchbook.entity.SketchbookCharacterMotion;
import com.lemon.backend.domain.users.user.entity.Users;
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
@SQLDelete(sql = "UPDATE letter SET is_deleted = TRUE WHERE letter_id = ?")
public class Letter extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "letter_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private Users sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private Users receiver;

    @Column(name= "content", length = 1000)
    private String content;

    @Column(name = "is_public")
    private boolean isPublic;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "sketchbook_id", nullable = true)
//    private Sketchbook sketchbook;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "characters_id", nullable = true)
//    private Characters characters;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sketchbookCharacterMotion_id", nullable = true)
    private SketchbookCharacterMotion sketchbookCharacterMotion; // 이름 수정

//    @OneToMany(mappedBy = "sketchbook", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Letter> letters = new ArrayList<>();

    public void changePublic(){
        this.isPublic = !this.isPublic;
    }
}
