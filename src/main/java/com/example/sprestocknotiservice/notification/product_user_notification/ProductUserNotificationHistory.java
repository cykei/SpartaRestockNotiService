package com.example.sprestocknotiservice.notification.product_user_notification;

import com.example.sprestocknotiservice.product.entity.Product;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


@ToString
@NoArgsConstructor
@Entity
public class ProductUserNotificationHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private Long productId;
    private Long userId;
    private Long productRound;

    @CreationTimestamp
    LocalDateTime createDateTime;

    private ProductUserNotificationHistory(Long productId, Long userId, Long productRound) {
        this.productId = productId;
        this.userId = userId;
        this.productRound = productRound;
    }

    public static ProductUserNotificationHistory makeHistory(Product product, ProductUserNotification notification) {
        return new ProductUserNotificationHistory(
                notification.getProductId(),
                notification.getUserId(),
                product.getRound()
        );
    }
}
