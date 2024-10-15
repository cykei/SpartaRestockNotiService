package com.example.sprestocknotiservice.product.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    private String name;

    private long round;

    private long productCount;

    @CreationTimestamp
    LocalDateTime createDateTime;

    public void updateRound() {
        this.round++;
    }

    public Product(String name, long round, long productCount, LocalDateTime createDateTime) {
        this.name = name;
        this.round = round;
        this.productCount = productCount;
        this.createDateTime = createDateTime;
    }
}
