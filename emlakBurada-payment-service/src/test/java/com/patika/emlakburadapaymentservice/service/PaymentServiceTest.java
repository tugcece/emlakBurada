package com.patika.emlakburadapaymentservice.service;

import com.patika.emlakburadapaymentservice.client.log.service.LogService;
import com.patika.emlakburadapaymentservice.dto.request.PaymentSaveRequest;
import com.patika.emlakburadapaymentservice.dto.response.PaymentResponse;
import com.patika.emlakburadapaymentservice.exception.ExceptionMessages;
import com.patika.emlakburadapaymentservice.model.Payment;
import com.patika.emlakburadapaymentservice.model.enums.PaymentStatus;
import com.patika.emlakburadapaymentservice.repository.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @InjectMocks
    private PaymentService paymentService;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private LogService logService;

    @Test
    void processPayment_shouldSavePaymentAndLogSuccess() {
        // given
        PaymentSaveRequest paymentRequest = new PaymentSaveRequest(1L, 1L, 100.0);

        Payment payment = new Payment();
        payment.setId(1L);
        payment.setUserId(paymentRequest.getUserId());
        payment.setTotalPrice(paymentRequest.getTotalPrice());
        payment.setStatus(PaymentStatus.PAID);
        payment.setPackageId(paymentRequest.getPackageId());
        payment.setPaymentDate(LocalDateTime.now());

        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        // when
        PaymentResponse response = paymentService.processPayment(paymentRequest);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getPaymentStatus()).isEqualTo(PaymentStatus.PAID);
        verify(paymentRepository, times(1)).save(any(Payment.class));
        verify(logService, times(1)).log("DbLogStrategy", "payment success");
    }

    @Test
    void findPaymentById_shouldReturnPaymentResponseWhenPaymentExists() {
        // given
        Long paymentId = 1L;
        Payment payment = new Payment();
        payment.setId(paymentId);
        payment.setStatus(PaymentStatus.PAID);

        when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(payment));

        // when
        PaymentResponse response = paymentService.findPaymentById(paymentId);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getPaymentStatus()).isEqualTo(PaymentStatus.PAID);
        verify(paymentRepository, times(1)).findById(paymentId);
    }

    @Test
    void findPaymentById_shouldThrowExceptionWhenPaymentNotFound() {
        // given
        Long paymentId = 1L;

        when(paymentRepository.findById(paymentId)).thenReturn(Optional.empty());

        // when
        RuntimeException exception = assertThrows(RuntimeException.class, () -> paymentService.findPaymentById(paymentId));

        // then
        assertThat(exception.getMessage()).isEqualTo(ExceptionMessages.PAYMENT_NOT_FOUND);
        verify(paymentRepository, times(1)).findById(paymentId);
    }
}
