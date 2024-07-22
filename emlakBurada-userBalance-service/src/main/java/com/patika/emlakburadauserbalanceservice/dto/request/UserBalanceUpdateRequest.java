package com.patika.emlakburadauserbalanceservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBalanceUpdateRequest {
    private Long id;
    private Long userId;
    private int remainingListings;
    private int periodOfValidity;

}
