package com.lemon.backend.domain.friend.entity;

import com.lemon.backend.domain.base.BaseEntity;
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
@SQLDelete(sql = "UPDATE groups_info SET is_deleted = TRUE WHERE groups_id = ?")
public class GroupsInfo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "groups_id")
    private Long id;

    public GroupsInfo(Long id, String groupName) {
        this.id = id;
        this.groupName = groupName;
    }

    @Setter
    @Column(name = "group_name")
    private String groupName;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Users owner;  // 그룹의 소유자

    @OneToMany(mappedBy = "groupsInfo", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<Friends> friendList;

}
