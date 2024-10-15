package com.example.sprestocknotiservice.notification.product_user_notification.service;

import com.example.sprestocknotiservice.notification.product_user_notification.ProductUserNotification;
import com.example.sprestocknotiservice.product.entity.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentLinkedQueue;

import static org.mockito.Mockito.when;

@SpringBootTest
class NotificationSenderTest {

    @Autowired
    NotificationSender notificationSender;

    private Product product;

    @BeforeEach
    void setUp() {
        product = Mockito.spy(new Product());
        when(product.getId()).thenReturn(1L);
        when(product.getRound()).thenReturn(1L);
        when(product.getProductCount()).thenReturn(1000L);
        notificationSender.registerReStock(product, 502L);

        for (int i=1; i<=502; i++) {
            notificationSender.register(new ProductUserNotification(1L, (long)i, false, null));
        }

    }

    @DisplayName("1초에 500개만 전송하는지 테스트 - 502개의 알림요청을 넣었으니 함수 시행후엔 큐에 2개가 남아있어야 한다.")
    @Test
    void sendNotification() {
        notificationSender.sendNotification();

        try {
            Field field = notificationSender.getClass().getDeclaredField("queue");
            field.setAccessible(true);
            ConcurrentLinkedQueue<ProductUserNotification> value = (ConcurrentLinkedQueue<ProductUserNotification>)field.get(notificationSender);
            Assertions.assertEquals(2, value.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}