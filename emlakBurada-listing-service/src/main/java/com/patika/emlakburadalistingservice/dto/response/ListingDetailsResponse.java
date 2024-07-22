package com.patika.emlakburadalistingservice.dto.response;

import com.patika.emlakburadalistingservice.model.enums.ListingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListingDetailsResponse {

    private String title;
    private ListingStatus listingStatus;
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