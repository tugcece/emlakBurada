package com.patika.emlakburadalistingservice.client.userBalance.service;

import com.patika.emlakburadalistingservice.client.userBalance.UserBalanceClient;
import com.patika.emlakburadalistingservice.client.userBalance.dto.response.UserBalanceResponse;
import com.patika.emlakburadalistingservice.dto.response.GenericResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserBalanceService {

    private final UserBalanceClient userBalanceClient;

    public UserBalanceResponse getBalanceAndExpireDateByUserId(Long id){
        GenericResponse<UserBalanceResponse> response = userBalanceClient.getByUserId(id);
        return response.getData();
    }

    public void reduceBalance(Long userId) {
        userBalanceClient.reduceBalanceByUserId(userId);
    }
}
