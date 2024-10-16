package com.example.sprestocknotiservice.notification.product_notification;

import com.example.sprestocknotiservice.product.entity.Product;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@ToString
@NoArgsConstructor
@Entity
public class ProductNotificationHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    private long productId;
    private long productRound;

    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;
    private Long lastUserId;

    @CreationTimestamp
    LocalDateTime createDateTime;

    public static ProductNotificationHistory inProgress(Product product) {
        return new ProductNotificationHistory(product.getId(), product.getRound(), ProductStatus.IN_PROGRESS);
    }

    public static ProductNotificationHistory canceled(Product product, boolean isSoldOut, long userId) {
        if (isSoldOut) {
            return new ProductNotificationHistory(product.getId(), product.getRound(), ProductStatus.CANCELED_BY_SOLD_OUT, userId);
        }
        return new ProductNotificationHistory(product.getId(), product.getRound(), ProductStatus.CANCELED_BY_ERROR, userId);
    }

    public static ProductNotificationHistory complete(Product product, long userId) {
        return new ProductNotificationHistory(product.getId(), product.getRound(), ProductStatus.COMPLETED, userId);
    }

    private ProductNotificationHistory(long productId, long productRound, ProductStatus productStatus) {
        this.productId = productId;
        this.productRound = productRound;
        this.productStatus = productStatus;
    }

    private ProductNotificationHistory(long productId, long productRound, ProductStatus productStatus, long userId) {
        this.productId = productId;
        this.productRound = productRound;
        this.productStatus = productStatus;
        this.lastUserId = userId;
    }
}
