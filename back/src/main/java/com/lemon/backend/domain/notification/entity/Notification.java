package com.lemon.backend.domain.notification.entity;

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
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id", nullable = false)
    private Long id;

    @Column(name = "type", nullable = false)
    private int type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private Users receiver;

    @Column(name = "friend_name")
    private String friendName;

    @Column(name = "is_check")
    @Builder.Default
    private Boolean isCheck = false;

    public void markAsChecked() {
        this.isCheck = true;
    }
}
