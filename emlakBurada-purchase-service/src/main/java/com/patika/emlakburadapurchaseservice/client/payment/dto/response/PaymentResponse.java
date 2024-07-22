package com.patika.emlakburadapurchaseservice.client.payment.dto.response;

import com.patika.emlakburadapurchaseservice.model.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class PaymentResponse {
    private Long id;
    private PaymentStatus paymentStatus;

}