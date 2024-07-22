package com.patika.emlakburadauserservice.dto.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UserSaveRequest {
    private String name;
    private String lastName;
    private String password;
    private String email;
    private String phone;
    private String address;
}
