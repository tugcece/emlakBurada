package com.patika.emlakburadauserbalanceservice.model;

import lombok.Data;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@Table(name = "user_balance")
public class UserBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id",nullable = false)
    private Long userId;
    @Column(name = "remaining_listins")
    private int remainingListings;
    @Column(name = "valid_until")
    private LocalDateTime validUntil;
    @Column(name = "create_date")
    private LocalDateTime createdAt;
    @Column(name = "update_date")
    private LocalDateTime updatedAt;

    public UserBalance(Long userId, LocalDateTime validUntil, int remainingListings ) {
        this.userId = userId;
        this.validUntil = validUntil;
        this.remainingListings = remainingListings;
    }

}