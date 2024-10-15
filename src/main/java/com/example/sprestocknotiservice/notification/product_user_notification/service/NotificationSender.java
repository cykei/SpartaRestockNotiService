package com.example.sprestocknotiservice.notification.product_user_notification.service;

import com.example.sprestocknotiservice.product.entity.Product;
import com.example.sprestocknotiservice.notification.product_notification.ProductNotificationHistory;
import com.example.sprestocknotiservice.notification.product_user_notification.ProductUserNotification;
import com.example.sprestocknotiservice.notification.product_user_notification.ProductUserNotificationHistory;
import com.example.sprestocknotiservice.notification.product_notification.repository.ProductNotificationHistoryRepository;
import com.example.sprestocknotiservice.notification.product_user_notification.repository.ProductUserNotificationHistoryRepository;
import com.example.sprestocknotiservice.notification.product_user_notification.repository.ProductUserNotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

@RequiredArgsConstructor
@Service
public class NotificationSender {
    private final ProductUserNotificationRepository productUserNotificationRepository;
    private final ProductUserNotificationHistoryRepository productUserNotificationHistoryRepository;
    private final ProductNotificationHistoryRepository productNotificationHistoryRepository;


    private ConcurrentHashMap<Long, Product> products = new ConcurrentHashMap<>();
    private ConcurrentLinkedQueue<ProductUserNotification> queue = new ConcurrentLinkedQueue();
    private ConcurrentHashMap<Long, Queue<Long>> productLastNotification = new ConcurrentHashMap<>();

    public void register(ProductUserNotification notification) {
        queue.add(notification);
    }

    public void registerProduct(Product product) {
        products.put(product.getId(), product);
    }

    public void registerReStock(Product product, long lastUserId) {
        products.put(product.getId(), product);

        Queue<Long> lastUserIds = productLastNotification.getOrDefault(product.getId(), new LinkedList<>());
        lastUserIds.add(lastUserId);
        productLastNotification.put(product.getId(), lastUserIds);
    }

    @Scheduled(cron = "* * * * * *")
    public void sendNotification() {
        int limit = 500;
        while (limit > 0 && !queue.isEmpty()) {

            // 1. 유저에게 메시지를 전송한다. poll 함과 동시에 보냈다고 가정
            ProductUserNotification productUserNotification = queue.poll();
            // 1-2. 재고상태가 0이면 보내면 안된다. product를 구매하면 products의 해당 productCount가 갱신된다고 가정한다.
            Product product = products.get(productUserNotification.getProductId());

            try {
                if (product.getProductCount() > 0) {
                    // 실제 알림전송은 여기서 한다고 가정한다.
                    // SlackUtil.sendNotification(productId, userId);
                    productUserNotification.setComplete();
                    productUserNotificationRepository.save(productUserNotification);
                    limit--;

                    // 2. 회차정보와 함께 유저 알람전송 히스토리를 기록한다.
                    ProductUserNotificationHistory history = ProductUserNotificationHistory.makeHistory(product, productUserNotification);
                    productUserNotificationHistoryRepository.save(history);
                }

                Long lastId = productLastNotification.get(product.getId()).peek();
                if (Objects.isNull(lastId)) {
                    throw new IllegalArgumentException("마지막으로 등록된 알림요청을 특정할 수 없습니다.");
                }

                if (lastId == productUserNotification.getUserId()) {
                    productLastNotification.get(product.getId()).poll();
                    ProductNotificationHistory completeHistory =
                            ProductNotificationHistory.complete(product, productUserNotification.getUserId());
                    productNotificationHistoryRepository.save(completeHistory);
                }
            } catch (Exception e) {
                ProductNotificationHistory errorHistory =
                        ProductNotificationHistory.canceled(product, false, productUserNotification.getUserId());
                productNotificationHistoryRepository.save(errorHistory);
            }
        }
    }
}
