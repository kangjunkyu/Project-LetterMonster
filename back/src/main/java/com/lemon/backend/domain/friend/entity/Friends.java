package com.lemon.backend.domain.friend.entity;

import com.lemon.backend.domain.letter.entity.Letter;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE friends SET is_deleted = TRUE WHERE friends_id = ?")
public class Friends {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "friends_id")
    private int id;

    @OneToMany(mappedBy = "friends", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UsersFriends> usersFriendsList;
}
