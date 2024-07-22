package com.patika.emlakburadauserbalanceservice.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserBalanceSaveRequest {
    private Long userId;
    private int remainingListings;
    private String validUntil;
}
