package com.patika.emlakburadapurchaseservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBalanceUpdateRequest {
    private Long id;
    private Long userId;
    private int remainingListings;
    private int periodOfValidity;

    public UserBalanceUpdateRequest(Long id, int remainingListings, int periodOfValidity) {
        this.id = id;
        this.remainingListings = remainingListings;
        this.periodOfValidity = periodOfValidity;
    }
}
