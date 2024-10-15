package com.example.sprestocknotiservice.notification.product_user_notification.repository;

import com.example.sprestocknotiservice.notification.product_user_notification.ProductUserNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductUserNotificationRepository extends JpaRepository<ProductUserNotification, Long> {
    //List<ProductUserNotification> findByProductIdAndProductRoundOrderByCreateDateTimeDesc(long productId, long productRound);

    //List<ProductUserNotification> findByProductIdNotSent(long productId);
    List<ProductUserNotification> findByProductIdAndCompleteIsFalse(long productId);

    //ProductUserNotification save(ProductUserNotification productUserNotification);
}
