package com.patika.emlakburadalistingservice.client.userBalance;

import com.patika.emlakburadalistingservice.client.userBalance.dto.response.UserBalanceResponse;
import com.patika.emlakburadalistingservice.dto.response.GenericResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "user-balance-service", url = "localhost:8097/api/v1/user-balance")
public interface UserBalanceClient {
    @GetMapping("/userid/{id}")
    GenericResponse<UserBalanceResponse> getByUserId(@PathVariable Long id);

    @PostMapping("/userid/{id}")
    ResponseEntity<UserBalanceResponse> reduceBalanceByUserId(@PathVariable Long id);
}