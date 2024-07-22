package com.patika.emlakburadalistingservice.client.user;

import com.patika.emlakburadalistingservice.client.user.dto.response.UserResponse;
import com.patika.emlakburadalistingservice.dto.response.GenericResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "user-service", url = "localhost:8091/api/v1/users")
public interface UserClient {
    @GetMapping("/id/{id}")
    GenericResponse<UserResponse> getById(@PathVariable Long id);

}