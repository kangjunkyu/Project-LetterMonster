package com.lemon.backend.domain.sketchbook.entity;

import com.lemon.backend.domain.base.BaseEntity;
import com.lemon.backend.domain.users.entity.Users;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
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
@SQLDelete(sql = "UPDATE sketchbook SET is_deleted = TRUE WHERE sketchbook_id = ?")
public class Sketchbook extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sketchbook_id")
    private Long id;

    @Setter
    @Column(name = "is_public")
//    @ColumnDefault("ture")
    @Builder.Default
    private Boolean isPublic = true;

    @Setter
    @Column(name = "share_link", length = 1000, nullable = true)
    private String shareLink;

    @Setter
    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", nullable = true)
    private Users users;

    public void addSketchbook(Users users) {
        this.users = users;
        users.getSketchbookList().add(this);
    }

    @Builder(toBuilder = true)
    Sketchbook(Users users, String name){
        this.name = name;
        addSketchbook(users);
    }
}
