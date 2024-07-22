package com.patika.emlakburadapaymentservice.converter;

import com.patika.emlakburadapaymentservice.dto.response.PaymentResponse;
import com.patika.emlakburadapaymentservice.model.Payment;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PaymentConverter {

    public static PaymentResponse toResponse(Payment payment) {
        return  PaymentResponse.builder()
                .id(payment.getId())
                .paymentStatus(payment.getStatus())
                .build();
    }
}
