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
@Builder(toBuilder = true)
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
    @Builder.Default
    private Boolean mainCharacter = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", nullable = true)
    private Users users;


    @OneToMany(mappedBy = "characters", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    @Builder.Default
    private List<CharacterMotion> characterMotionList = new ArrayList<>();


    public void addCharacter(Users user){
        this.users = user;
        user.getCharacterList().add(this);
    }

    @Builder(toBuilder = true)
    Characters(String nickname, Boolean mainCharacter, Users user){
        this.nickname = nickname;
        this.mainCharacter = mainCharacter;
        this.users = user;
        addCharacter(user);
    }

    public void changeMainCharacter() {
        this.mainCharacter = !this.getMainCharacter();
    }

}
