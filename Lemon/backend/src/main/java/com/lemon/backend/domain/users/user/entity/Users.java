package com.lemon.backend.domain.users.user.entity;

import com.lemon.backend.domain.base.BaseEntity;
import com.lemon.backend.domain.characters.character.entity.Characters;
import com.lemon.backend.domain.sketchbook.entity.Sketchbook;
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
@SQLDelete(sql = "UPDATE users SET is_deleted = TRUE WHERE users_id = ?")
public class Users extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "users_id")
    private Integer id;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "nickname_tag")
    private String nicknameTag;

    @Column(name = "provider")
    private Social provider;

    @Column(name = "kakao_id")
    private Integer kakaoId;

    @Builder.Default
    @OneToMany(mappedBy = "users", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private List<Characters> characterList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "users", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private List<Sketchbook> sketchbookList = new ArrayList<>();

}
