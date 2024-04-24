package com.lemon.backend.domain.friend.entity;

import com.lemon.backend.domain.users.user.entity.Users;
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
@SQLDelete(sql = "UPDATE usersfriends SET is_deleted = TRUE WHERE usersfriends_id = ?")
public class UsersFriends {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usersfriends_id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private Users users;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friends_id")
    private Friends firends;

    @Column(name = "group_name")
    private String groupName;
}
