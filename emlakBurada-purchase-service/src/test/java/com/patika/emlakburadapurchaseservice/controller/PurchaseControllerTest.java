package com.patika.emlakburadapurchaseservice.controller;

import com.patika.emlakburadapurchaseservice.dto.request.PurchaseSaveRequest;
import com.patika.emlakburadapurchaseservice.service.PurchaseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PurchaseControllerTest {

    @Mock
    private PurchaseService purchaseService;

    @InjectMocks
    private PurchaseController purchaseController;

    private PurchaseSaveRequest purchaseSaveRequest;

    @BeforeEach
    void setUp() {
        purchaseSaveRequest = new PurchaseSaveRequest();
        purchaseSaveRequest.setUserId(1L);
        purchaseSaveRequest.setPackageId(1L);
        purchaseSaveRequest.setTotalPrice(100.0);
    }

    @Test
    void purchase_successfully() {
        // given
        doNothing().when(purchaseService).purchasePackage(any(PurchaseSaveRequest.class));

        // when
        ResponseEntity<Void> response = purchaseController.purchase(purchaseSaveRequest);

        // then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(purchaseService, times(1)).purchasePackage(any(PurchaseSaveRequest.class));
    }

    @Test
    void purchase_shouldThrowException_whenServiceFails() {
        // given
        doThrow(new RuntimeException("Service error")).when(purchaseService).purchasePackage(any(PurchaseSaveRequest.class));

        // when
        RuntimeException exception = assertThrows(RuntimeException.class, () -> purchaseController.purchase(purchaseSaveRequest));

        // then
        assertEquals("Service error", exception.getMessage());
        verify(purchaseService, times(1)).purchasePackage(any(PurchaseSaveRequest.class));
    }
}
