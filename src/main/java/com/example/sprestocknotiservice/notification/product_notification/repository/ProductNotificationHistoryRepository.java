package com.example.sprestocknotiservice.notification.product_notification.repository;

import com.example.sprestocknotiservice.notification.product_notification.ProductNotificationHistory;
import com.example.sprestocknotiservice.notification.product_notification.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductNotificationHistoryRepository extends JpaRepository<ProductNotificationHistory, Long> {

    ProductNotificationHistory findFirstByProductIdAndProductRoundAndProductStatusIn(long productId, long productRound, List<ProductStatus> status);
}
