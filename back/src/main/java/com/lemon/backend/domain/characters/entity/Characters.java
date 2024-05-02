package com.lemon.backend.domain.characters.entity;

import com.lemon.backend.domain.base.BaseEntity;
import com.lemon.backend.domain.users.user.entity.Users;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
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
    private Boolean mainCharacter;

    @Column(name = "image_url", columnDefinition = "TEXT")
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", nullable = true)
    private Users users;

    @OneToMany(mappedBy = "characters", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})

    private List<CharacterMotion> characterMotionList;


    public void addCharacter(Users user){
        user.getCharacterList().add(this);
    }

    @Builder
    Characters(String nickname, Users users){
        this.nickname = nickname;
        this.users = users;
        this.url = "";
        this.mainCharacter =false;
        this.characterMotionList = new ArrayList<>();
        addCharacter(users);
    }

    @Builder
    Characters(String nickname){
        this.nickname = nickname;
        this.users = null;
        this.url = "";
        this.mainCharacter =false;
        this.characterMotionList = new ArrayList<>();
    }

    public void changeMainCharacter() {
        this.mainCharacter = !this.mainCharacter;
    }
    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    public void changeUrl(String url) {
        this.url = url;
    }
    public void deleteUrl() {
        this.url = "";
    }
    public void deleteUser() {this.users = null;}

}
