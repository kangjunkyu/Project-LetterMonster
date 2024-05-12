package com.lemon.backend.domain.notification.entity;

import com.lemon.backend.domain.base.BaseEntity;
import com.lemon.backend.domain.users.user.entity.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE notification SET is_deleted = TRUE WHERE notification_id = ?")
public class Notification extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id", nullable = false)
    private Long id;

    public Notification(Long id) {
        this.id = id;
    }

    public Notification(Long id, int type, String sketchbookName, String sketchbookTag, String sketchbookUuid, String friendName, String friendTag, Boolean isCheck) {
        this.id = id;
        this.type = type;
        this.sketchbookName = sketchbookName;
        this.sketchbookTag = sketchbookTag;
        this.sketchbookUuid = sketchbookUuid;
        this.friendName = friendName;
        this.friendTag = friendTag;
        this.isCheck = isCheck;
    }

    @Column(name = "type", nullable = false)
    private int type;

    @Column(name = "sketchbook_name")
    private String sketchbookName;

    @Column(name = "sketchbook_tag")
    private String sketchbookTag;

    @Column(name = "sketchbook_uuid")
    private String sketchbookUuid;

    @Column(name = "friend_name")
    private String friendName;

    @Column(name = "friend_tag")
    private String friendTag;

    @Column(name = "is_check")
    @Builder.Default
    private Boolean isCheck = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private Users receiver;

    public void markAsChecked() {
        this.isCheck = true;
    }
}
