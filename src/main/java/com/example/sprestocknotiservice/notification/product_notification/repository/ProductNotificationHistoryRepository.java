package com.example.sprestocknotiservice.notification.product_notification.repository;

import com.example.sprestocknotiservice.notification.product_notification.ProductNotificationHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductNotificationHistoryRepository extends JpaRepository<ProductNotificationHistory, Long> {
}
