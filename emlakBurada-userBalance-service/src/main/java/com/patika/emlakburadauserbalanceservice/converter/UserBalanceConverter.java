package com.patika.emlakburadauserbalanceservice.converter;

import com.patika.emlakburadauserbalanceservice.dto.response.UserBalanceResponse;
import com.patika.emlakburadauserbalanceservice.model.UserBalance;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserBalanceConverter {

    public static UserBalanceResponse toResponse(UserBalance userBalance) {
        return UserBalanceResponse.builder()
                .userId(userBalance.getUserId())
                .remainingListings(userBalance.getRemainingListings())
                .validUntil(userBalance.getValidUntil())
                .build();
    }

}
