package com.patika.emlakburadalistingservice.client.userBalance.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UserBalanceResponse {
    private Long userId;
    private int remainingListings;
    private LocalDateTime validUntil;
}