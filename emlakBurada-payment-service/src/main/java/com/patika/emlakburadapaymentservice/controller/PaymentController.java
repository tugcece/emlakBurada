package com.patika.emlakburadapaymentservice.controller;

import com.patika.emlakburadapaymentservice.dto.request.PaymentSaveRequest;
import com.patika.emlakburadapaymentservice.dto.response.GenericResponse;
import com.patika.emlakburadapaymentservice.dto.response.PaymentResponse;
import com.patika.emlakburadapaymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponse> makePayment(@RequestBody PaymentSaveRequest paymentRequest) {
        PaymentResponse paymentResponse = paymentService.processPayment(paymentRequest);
        return new ResponseEntity<>(paymentResponse, HttpStatus.CREATED);
    }

    @GetMapping("/id/{id}")
    public GenericResponse<PaymentResponse> getPaymentById(@PathVariable("id") Long id) {
        PaymentResponse paymentResponse = paymentService.findPaymentById(id);
        return GenericResponse.success(paymentResponse);
    }
}
