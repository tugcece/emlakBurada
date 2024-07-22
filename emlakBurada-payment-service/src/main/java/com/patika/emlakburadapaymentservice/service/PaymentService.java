package com.patika.emlakburadapaymentservice.service;

import com.patika.emlakburadapaymentservice.client.log.service.LogService;
import com.patika.emlakburadapaymentservice.converter.PaymentConverter;
import com.patika.emlakburadapaymentservice.dto.request.PaymentSaveRequest;
import com.patika.emlakburadapaymentservice.dto.response.PaymentResponse;
import com.patika.emlakburadapaymentservice.exception.ExceptionMessages;
import com.patika.emlakburadapaymentservice.model.Payment;
import com.patika.emlakburadapaymentservice.model.enums.PaymentStatus;
import com.patika.emlakburadapaymentservice.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor

public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final LogService logService;
    /* With the request from the Purchase service, the payment is created
     * and a record is added to the database.
     */
    @Transactional
    public PaymentResponse processPayment(PaymentSaveRequest paymentRequest) {
        Payment payment = new Payment();
        payment.setUserId(paymentRequest.getUserId());
        payment.setTotalPrice(paymentRequest.getTotalPrice());
        payment.setStatus(PaymentStatus.PAID);
        payment.setPackageId(paymentRequest.getPackageId());
        payment.setPaymentDate(LocalDateTime.now());
        paymentRepository.save(payment);
       // logService.log("DbLogStrategy","payment success");
        return new PaymentResponse(payment.getId(), payment.getStatus());
    }

    public PaymentResponse findPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(ExceptionMessages.PAYMENT_NOT_FOUND));
        return PaymentConverter.toResponse(payment);
    }

}


