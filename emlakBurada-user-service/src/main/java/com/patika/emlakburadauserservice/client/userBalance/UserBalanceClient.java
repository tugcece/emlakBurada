package com.patika.emlakburadauserservice.client.userBalance;

import com.patika.emlakburadauserservice.dto.request.UserBalanceSaveRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-balance-service", url = "http://localhost:8097/api/v1/user-balance")
public interface UserBalanceClient {

    @PostMapping("")
    ResponseEntity<Void> createUserBalance(@RequestBody UserBalanceSaveRequest request);


}
