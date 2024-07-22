package com.patika.emlakburadalistingservice.dto.response;


import com.patika.emlakburadalistingservice.model.enums.ListingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListingResponse {

    private Long id;
    private String title;
    private Double price;
    private ListingStatus listingStatus;
    private LocalDateTime createDate;
    private LocalDateTime expirationDate;
    private int totalPage;

}
