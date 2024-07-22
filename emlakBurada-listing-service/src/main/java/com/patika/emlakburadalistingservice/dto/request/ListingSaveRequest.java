package com.patika.emlakburadalistingservice.dto.request;


import com.patika.emlakburadalistingservice.model.enums.ListingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListingSaveRequest {

    private String title;
    private Long  userId;
    private ListingStatus listingStatus;
    private LocalDateTime createDate;
    private LocalDateTime expirationDate;
    private Double price;
    private String details;
    private String address;
    private String summary;
    private Integer roomNumber;
    private Integer size;
    private Integer floor;
    private Integer age;
    private Double deposit;
}

