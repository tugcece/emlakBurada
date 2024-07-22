package com.patika.emlakburadauserservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UserResponse {
    private Long id;
    private String name;
    private String lastName;
    private String password;
    private String email;
    private String phone;
    private String address;
}