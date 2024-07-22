package com.patika.emlakburadapurchaseservice.service;

import com.patika.emlakburadapurchaseservice.client.log.service.LogService;
import com.patika.emlakburadapurchaseservice.client.payment.dto.response.PaymentResponse;
import com.patika.emlakburadapurchaseservice.client.payment.service.PaymentService;
import com.patika.emlakburadapurchaseservice.dto.request.PaymentSaveRequest;
import com.patika.emlakburadapurchaseservice.dto.request.PurchaseSaveRequest;
import com.patika.emlakburadapurchaseservice.dto.request.UserBalanceUpdateRequest;
import com.patika.emlakburadapurchaseservice.exception.ExceptionMessages;
import com.patika.emlakburadapurchaseservice.model.Purchase;
import com.patika.emlakburadapurchaseservice.model.enums.PaymentStatus;
import com.patika.emlakburadapurchaseservice.model.enums.PurchaseStatus;
import com.patika.emlakburadapurchaseservice.producer.MessageProducer;
import com.patika.emlakburadapurchaseservice.repository.PurchaseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class PurchaseServiceTest {

    @InjectMocks
    private PurchaseService purchaseService;

    @Mock
    private PurchaseRepository purchaseRepository;

    @Mock
    private PaymentService paymentService;

    @Mock
    private MessageProducer messageProducer;

    @Mock
    private LogService logService;

    @Test
    void purchasePackage_shouldCreatePurchase_whenPaymentIsSuccessful() {
        // given
        PurchaseSaveRequest purchaseRequest = new PurchaseSaveRequest();
        purchaseRequest.setUserId(1L);
        purchaseRequest.setPackageId(1L);
        purchaseRequest.setTotalPrice(100.0);

        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setPaymentStatus(PaymentStatus.PAID);

        when(paymentService.makePayment(any(PaymentSaveRequest.class))).thenReturn(paymentResponse);

        // when
        purchaseService.purchasePackage(purchaseRequest);

        // then
        verify(purchaseRepository, times(1)).save(any(Purchase.class));
        verify(logService, times(1)).log("DbLogStrategy", "Purchase successfully created");
        verify(messageProducer, times(1)).sendMessage(any(UserBalanceUpdateRequest.class));
    }

    @Test
    void purchasePackage_shouldThrowException_whenPaymentIsDeclined() {
        // given
        PurchaseSaveRequest purchaseRequest = new PurchaseSaveRequest();
        purchaseRequest.setUserId(1L);
        purchaseRequest.setPackageId(1L);
        purchaseRequest.setTotalPrice(100.0);

        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setPaymentStatus(PaymentStatus.DECLINED);

        when(paymentService.makePayment(any(PaymentSaveRequest.class))).thenReturn(paymentResponse);

        // when
        RuntimeException exception = assertThrows(RuntimeException.class, () -> purchaseService.purchasePackage(purchaseRequest));

        // then
        assertThat(exception.getMessage()).isEqualTo(ExceptionMessages.PAYMENT_DECLINED);
        verify(logService, times(1)).log("DbLogStrategy", ExceptionMessages.PAYMENT_DECLINED);
        verify(purchaseRepository, never()).save(any(Purchase.class));
        verify(messageProducer, never()).sendMessage(any(UserBalanceUpdateRequest.class));
    }
}
