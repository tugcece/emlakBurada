package com.patika.emlakburadauserbalanceservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserBalanceResponse {

    private Long userId;
    private int remainingListings;
    private LocalDateTime validUntil;
}
