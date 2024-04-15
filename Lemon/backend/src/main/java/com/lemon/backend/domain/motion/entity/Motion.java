package com.lemon.backend.domain.motion.entity;

import com.lemon.backend.domain.base.BaseEntity;
import com.lemon.backend.domain.characterMotion.entity.CharacterMotion;
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
@SQLDelete(sql = "UPDATE motion SET is_deleted = TRUE WHERE motion_id = ?")
public class Motion extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "motion_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "motion", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private List<CharacterMotion> characterMotionList = new ArrayList<>();
}
