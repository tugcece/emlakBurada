package com.patika.emlakburadapaymentservice.dto;

import com.patika.emlakburadapaymentservice.model.enums.ListingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListingDto {
    private int id;
    private String title;
    private ListingStatus listingStatus;
    private LocalDateTime createDate;
    private LocalDateTime expirationDate;

    public ListingDto(String title, ListingStatus listingStatus, LocalDateTime createDate, LocalDateTime expirationDate) {
        this.title = title;
        this.listingStatus = listingStatus;
        this.createDate = createDate;
        this.expirationDate = expirationDate;
    }
}