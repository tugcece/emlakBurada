package com.patika.emlakburadapaymentservice.model;

import com.patika.emlakburadapaymentservice.model.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
@Table(name = "payment")

public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "package_id",nullable = false)
    private Long packageId;
    @Column(name = "user_id",nullable = false)
    private Long userId;
    @Column(name = "payment_date")
    private LocalDateTime paymentDate;
    @Column (name =  "total_price")
    private Double totalPrice;
    @Enumerated(EnumType.STRING)
    @Column (name = "status")
    private PaymentStatus status;


    public Payment(Long userId, Long packageId, LocalDateTime paymentDate, Double totalPrice) {
        this.userId = userId;
        this.packageId = packageId;
        this.paymentDate = paymentDate;
        this.totalPrice = totalPrice;
    }
}

