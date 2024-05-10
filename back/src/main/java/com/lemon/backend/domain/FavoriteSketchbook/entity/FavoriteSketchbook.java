package com.lemon.backend.domain.FavoriteSketchbook.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lemon.backend.domain.base.BaseEntity;
import com.lemon.backend.domain.sketchbook.entity.Sketchbook;
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
@SQLDelete(sql = "UPDATE favorite_sketchbook SET is_deleted = TRUE WHERE favoritesketchbook_id = ?")
public class FavoriteSketchbook extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favoritesketchbook_id")
    private Long id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "sketchbook_id")
    private Sketchbook sketchbook;

}
