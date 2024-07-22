package com.patika.emlakburadapurchaseservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PurchaseResponse {
    private Long purchaseId;
    private LocalDateTime expirationDate;
}
