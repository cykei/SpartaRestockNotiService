package com.example.sprestocknotiservice.notification.product_user_notification;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Entity
public class ProductUserNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;
    private long productId;
    private long userId;
    private boolean complete;

    @CreationTimestamp
    LocalDateTime createDateTime;

    @UpdateTimestamp
    LocalDateTime updateDateTime;

    public void setComplete() {
        this.complete = true;
    }

    public ProductUserNotification(long productId, long userId, boolean complete, LocalDateTime createDateTime) {
        this.productId = productId;
        this.userId = userId;
        this.complete = complete;
        this.createDateTime = createDateTime;
    }
}
