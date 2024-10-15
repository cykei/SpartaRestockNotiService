package com.example.sprestocknotiservice.notification.product_notification.service;

import com.example.sprestocknotiservice.product.entity.Product;
import com.example.sprestocknotiservice.notification.product_notification.ProductNotificationHistory;
import com.example.sprestocknotiservice.notification.product_user_notification.ProductUserNotification;
import com.example.sprestocknotiservice.notification.product_notification.repository.ProductNotificationHistoryRepository;
import com.example.sprestocknotiservice.product.repository.ProductRepository;
import com.example.sprestocknotiservice.notification.product_user_notification.repository.ProductUserNotificationRepository;
import com.example.sprestocknotiservice.notification.product_user_notification.service.NotificationSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RestockNotificationService {
    private final ProductRepository productRepository;
    private final ProductUserNotificationRepository productUserNotificationRepository;
    private final ProductNotificationHistoryRepository productNotificationHistoryRepository;
    private final NotificationSender notificationSender;

    @Transactional
    public void sendNotifications(Long productId) {
        Product product = productRepository.findById(productId).
                orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));

        // 0. 재입고 알림을 신청한 사람들의 정보를 가져온다.
        List<ProductUserNotification> productUserNotifications = productUserNotificationRepository.findByProductIdAndCompleteIsFalse(productId);

        // 1. 재입고 알림을 전송하기전, 상품의 재입고 회차를 1회 증가시킨다.
        product.updateRound();

        // 2. 재입고 알림대기열에 등록한다.
        notificationSender.registerReStock(product, getLastUserId(productUserNotifications));
        long stockCount = product.getProductCount();
        ProductNotificationHistory startNotification = ProductNotificationHistory.inProgress(product);

        // 3. 재입고 히스토리를 쌓는다.
        productNotificationHistoryRepository.save(startNotification);

        for (ProductUserNotification productUserNotification : productUserNotifications) {

            // 재고를 소진시 중단한다.
            if (stockCount <= 0) {
                ProductNotificationHistory failNotification = ProductNotificationHistory.canceled(
                        product, true, productUserNotification.getUserId());
                productNotificationHistoryRepository.save(failNotification);
                break;
            }

            notificationSender.register(productUserNotification);
            stockCount--;
        }

    }

    private long getLastUserId(List<ProductUserNotification> productUserNotifications) {
        return productUserNotifications.get(productUserNotifications.size()-1).getUserId();
    }

}
