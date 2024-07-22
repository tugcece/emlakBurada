package com.patika.emlakburadapurchaseservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentSaveRequest {
        private Long userId;
        private Long packageId;
        private Double totalPrice;
}
