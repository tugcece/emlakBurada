package com.patika.emlakburadauserbalanceservice.controller;

import com.patika.emlakburadauserbalanceservice.dto.request.UserBalanceSaveRequest;
import com.patika.emlakburadauserbalanceservice.dto.response.GenericResponse;
import com.patika.emlakburadauserbalanceservice.dto.response.UserBalanceResponse;
import com.patika.emlakburadauserbalanceservice.service.UserBalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user-balance")
public class UserBalanceController {

    private final UserBalanceService userBalanceService;

    @GetMapping("/userid/{id}")
    public GenericResponse<UserBalanceResponse> getPaymentById(@PathVariable("id") Long userId) {
        UserBalanceResponse userBalanceResponse = userBalanceService.getUserBalanceByUserId(userId);
        return GenericResponse.success(userBalanceResponse);
    }

    @PostMapping("/userid/{id}")
    ResponseEntity<UserBalanceResponse> reduceBalanceByUserId(@PathVariable("id") Long userId){
        userBalanceService.reduceBalance(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<Void> createUserBalance(@RequestBody UserBalanceSaveRequest request) {
        userBalanceService.createUserBalance(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
