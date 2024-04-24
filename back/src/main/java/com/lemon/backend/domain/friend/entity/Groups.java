package com.lemon.backend.domain.friend.entity;

import com.lemon.backend.domain.users.user.entity.Users;
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
@SQLDelete(sql = "UPDATE groups SET is_deleted = TRUE WHERE groups_id = ?")
public class Groups {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "groups_id")
    private Long id;

    @Setter
    @Column(name = "group_name")
    private String groupName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Users owner;  // 그룹의 소유자

    @OneToMany(mappedBy = "groups", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private List<Friends> friendList;

}
