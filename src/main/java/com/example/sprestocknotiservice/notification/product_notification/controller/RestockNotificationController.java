package com.example.sprestocknotiservice.notification.product_notification.controller;

import com.example.sprestocknotiservice.notification.product_notification.service.RestockNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("products")
@RequiredArgsConstructor
public class RestockNotificationController {
    final private RestockNotificationService restockNotificationService;

    @PostMapping("{productId}/notifications/re-stock")
    public void sendNotifications(@PathVariable long productId) {
        restockNotificationService.sendNotifications(productId);
    }
}
