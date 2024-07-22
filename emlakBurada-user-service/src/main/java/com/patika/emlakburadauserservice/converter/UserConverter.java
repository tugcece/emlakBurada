package com.patika.emlakburadauserservice.converter;

import com.patika.emlakburadauserservice.dto.request.UserSaveRequest;
import com.patika.emlakburadauserservice.dto.response.UserResponse;
import com.patika.emlakburadauserservice.model.User;
import com.patika.emlakburadauserservice.util.HashUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Component
public class UserConverter {

    public static User toUser(UserSaveRequest userSaveRequest) {
        String hashedPassword = HashUtil.generate(userSaveRequest.getPassword());
        return User.builder()
                .name(userSaveRequest.getName())
                .lastName(userSaveRequest.getLastName())
                .password(hashedPassword)
                .email(userSaveRequest.getEmail())
                .phone(userSaveRequest.getPhone())
                .address(userSaveRequest.getAddress())
                .createDate(LocalDateTime.now())
                .build();
    }

    public static UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .lastName(user.getLastName())
                .password(user.getPassword())
                .email(user.getEmail())
                .phone(user.getPhone())
                .address(user.getAddress())
                .build();
    }
}
