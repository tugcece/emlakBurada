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
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final PaymentService paymentService;
    private final MessageProducer messageProducer;
    private final LogService logService;

    /* Initiates a payment transaction by communicating with the payment service.
     * If a smooth payment is made (PAID), it performs the necessary operations to
     * create a purchase and makes an async request with RabbitMq to set the
     * user balance value.
     */
    @Transactional
    public void purchasePackage(PurchaseSaveRequest purchaseRequest) {
        PaymentResponse payment = paymentService.makePayment(preparePaymentRequest(purchaseRequest));
        if (PaymentStatus.PAID.equals(payment.getPaymentStatus())) {
            Purchase purchase = createPurchase(purchaseRequest);
            purchaseRepository.save(purchase);
            logService.log("DbLogStrategy","Purchase successfully created");
            UserBalanceUpdateRequest updateRequest = new UserBalanceUpdateRequest( purchase.getId(), purchase.getUserId(), 20,30);
            messageProducer.sendMessage(updateRequest);
        } else {
            logService.log("DbLogStrategy",ExceptionMessages.PAYMENT_DECLINED);
            throw new RuntimeException(ExceptionMessages.PAYMENT_DECLINED);
        }
    }

    private Purchase createPurchase(PurchaseSaveRequest purchaseRequest) {
        Purchase purchase = new Purchase();
        purchase.setUserId(purchaseRequest.getUserId());
        purchase.setPackageId(purchaseRequest.getPackageId());
        purchase.setTotalPrice(purchaseRequest.getTotalPrice());
        purchase.setPurchaseDate(LocalDateTime.now());
        purchase.setExpirationDate(LocalDateTime.now().plusDays(30));
        purchase.setStatus(PurchaseStatus.PURCHASED);
        return purchase;
    }

    private PaymentSaveRequest preparePaymentRequest(PurchaseSaveRequest purchaseRequest) {
        PaymentSaveRequest paymentSaveRequest = new PaymentSaveRequest();
        paymentSaveRequest.setUserId(purchaseRequest.getUserId());
        paymentSaveRequest.setPackageId(purchaseRequest.getPackageId());
        paymentSaveRequest.setTotalPrice(purchaseRequest.getTotalPrice());

        return paymentSaveRequest;
    }

}

