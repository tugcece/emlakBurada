package com.patika.emlakburadapurchaseservice.client.payment.service;

import com.patika.emlakburadapurchaseservice.client.payment.PaymentClient;
import com.patika.emlakburadapurchaseservice.client.payment.dto.response.PaymentResponse;
import com.patika.emlakburadapurchaseservice.dto.request.PaymentSaveRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class PaymentService {

    private final PaymentClient paymentClient;

    public PaymentResponse makePayment(PaymentSaveRequest paymentSaveRequest){
        ResponseEntity<PaymentResponse> response = paymentClient.makePayment(paymentSaveRequest);
        if (response.getStatusCode() != HttpStatus.CREATED) {
            log.error("Payment failed");
        }
        return response.getBody();
    }

}
