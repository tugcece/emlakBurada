package com.patika.emlakburadapurchaseservice.model;

import com.patika.emlakburadapurchaseservice.model.enums.PurchaseStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
@Table(name = "purchases")

public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "package_id",nullable = false)
    private Long packageId;
    @Column(name = "user_id",nullable = false)
    private Long userId;
    @Column(name = "purchase_date")
    private LocalDateTime purchaseDate;
    @Column (name =  "total_price")
    private Double totalPrice;
    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;
    @Enumerated(EnumType.STRING)
    @Column (name = "status")
    private PurchaseStatus status;

}
