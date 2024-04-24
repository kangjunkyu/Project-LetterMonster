package com.lemon.backend.domain.letter.entity;

import com.lemon.backend.domain.base.BaseEntity;
import com.lemon.backend.domain.characters.entity.Characters;
import com.lemon.backend.domain.sketchbook.entity.Sketchbook;
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
@SQLDelete(sql = "UPDATE letter SET is_deleted = TRUE WHERE letter_id = ?")
public class Letter extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "letter_id")
    private Long id;

    @Column(name = "sender")
    private Integer sender;

    @Column(name = "receiver")
    private Integer receiver;

    @Column(name= "content", length = 1000)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sketchbook_id", nullable = true)
    private Sketchbook sketchbook;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "characters_id", nullable = true)
    private Characters characters;


}
