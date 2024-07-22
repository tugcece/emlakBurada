package com.patika.emlakburadalistingservice.dto.request;

import com.patika.emlakburadalistingservice.model.enums.ListingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ListingSearchRequest extends BaseSearchRequest{
    private Long userId;
    private ListingStatus listingStatus;
}
