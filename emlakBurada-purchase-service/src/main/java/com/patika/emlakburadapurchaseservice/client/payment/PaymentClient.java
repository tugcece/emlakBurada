package com.patika.emlakburadapurchaseservice.client.payment;

import com.patika.emlakburadapurchaseservice.client.payment.dto.response.PaymentResponse;
import com.patika.emlakburadapurchaseservice.dto.request.PaymentSaveRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "payment-service", url = "localhost:8095/api/v1/payments")
public interface PaymentClient {

    @PostMapping("")
    ResponseEntity<PaymentResponse> makePayment(@RequestBody PaymentSaveRequest paymentRequest);

}