//package com.example.sprestocknotiservice.repository;
//
//import com.example.sprestocknotiservice.notification.product_user_notification.ProductUserNotification;
//import com.example.sprestocknotiservice.domain.QProductUserNotification;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public class ProductUserNotificationRepositoryImpl implements ProductUserNotificationRepository{
//
//    private static final QProductUserNotification productUserNotification = new QProductUserNotification("productUserNotification");
//    private final JPAQueryFactory queryFactory;
//
//    public ProductUserNotificationRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
//        this.queryFactory = jpaQueryFactory;
//    }
//
//    @Override
//    public List<ProductUserNotification> findByProductIdNotSent(long productId) {
//        return queryFactory.selectFrom(productUserNotification)
//                .where(productUserNotification.productId.eq(productId)
//                        .and(productUserNotification.complete.eq(false)))
//                .fetch();
//    }
//}
