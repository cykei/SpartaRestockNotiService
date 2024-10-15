package com.example.sprestocknotiservice.notification.product_user_notification.repository;

import com.example.sprestocknotiservice.notification.product_user_notification.ProductUserNotificationHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductUserNotificationHistoryRepository extends JpaRepository<ProductUserNotificationHistory, Long> {
}
