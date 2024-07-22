package com.patika.emlakburadapurchaseservice.dto.request;

import com.patika.emlakburadapurchaseservice.model.enums.PurchaseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseSaveRequest {

    private Long packageId;
    private Long userId;
    private LocalDateTime purchaseDate;
    private Double totalPrice;
    private PurchaseStatus status;
    private LocalDateTime expirationDate;
}
