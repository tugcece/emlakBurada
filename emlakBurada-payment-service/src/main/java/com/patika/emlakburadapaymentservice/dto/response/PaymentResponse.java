package com.patika.emlakburadapaymentservice.dto.response;

import com.patika.emlakburadapaymentservice.model.enums.PaymentStatus;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PaymentResponse {
    private Long id;
    private PaymentStatus paymentStatus;
}
