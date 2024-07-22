package com.patika.emlakburadauserbalanceservice.controller;

import com.patika.emlakburadauserbalanceservice.dto.response.GenericResponse;
import com.patika.emlakburadauserbalanceservice.dto.response.UserBalanceResponse;
import com.patika.emlakburadauserbalanceservice.service.UserBalanceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserBalanceControllerTest {

    @Mock
    private UserBalanceService userBalanceService;

    @InjectMocks
    private UserBalanceController userBalanceController;

    private UserBalanceResponse userBalanceResponse;

    @BeforeEach
    void setUp() {
        userBalanceResponse = UserBalanceResponse.builder()
                .userId(1L)
                .remainingListings(5)
                .validUntil(null)
                .build();
    }

    @Test
    void testGetPaymentById() {
        Long userId = 1L;
        when(userBalanceService.getUserBalanceByUserId(userId)).thenReturn(userBalanceResponse);

        GenericResponse<UserBalanceResponse> response = userBalanceController.getPaymentById(userId);

        assertNotNull(response);
        assertEquals(userBalanceResponse, response.getData());
        assertEquals(HttpStatus.OK, response.getHttpStatus());
    }

    @Test
    void testReduceBalanceByUserId() {
        Long userId = 1L;

        doNothing().when(userBalanceService).reduceBalance(userId);

        ResponseEntity<UserBalanceResponse> response = userBalanceController.reduceBalanceByUserId(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userBalanceService, times(1)).reduceBalance(userId);
    }
}
