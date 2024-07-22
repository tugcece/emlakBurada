package com.patika.emlakburadalistingservice.client.user.service;

import com.patika.emlakburadalistingservice.client.user.UserClient;
import com.patika.emlakburadalistingservice.client.user.dto.response.UserResponse;
import com.patika.emlakburadalistingservice.dto.response.GenericResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {

    private final UserClient userClient;

    public UserResponse getUserById(Long id){
        GenericResponse<UserResponse> response = userClient.getById(id);
        if (response == null || !HttpStatus.OK.equals(response.getHttpStatus())) {
            log.error("Error Message: {}", response.getMessage());
        }
        return response.getData();
    }


}
