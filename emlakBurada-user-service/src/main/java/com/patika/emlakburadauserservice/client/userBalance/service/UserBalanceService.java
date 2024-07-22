package com.patika.emlakburadauserservice.client.userBalance.service;

import com.patika.emlakburadauserservice.client.userBalance.UserBalanceClient;
import com.patika.emlakburadauserservice.dto.request.UserBalanceSaveRequest;
import com.patika.emlakburadauserservice.exception.EmlakBuradaException;
import com.patika.emlakburadauserservice.exception.ExceptionMessages;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserBalanceService {

    private final UserBalanceClient userBalanceClient;

    public ResponseEntity<Void> createUserBalance(UserBalanceSaveRequest userBalanceRequest){
        try {
            userBalanceClient.createUserBalance(userBalanceRequest);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            log.error("Failed to create user balance for user id: {}", userBalanceRequest.getUserId(), e);
            throw new EmlakBuradaException(ExceptionMessages.USER_BALANCE_FAILED);
        }
    }


}
