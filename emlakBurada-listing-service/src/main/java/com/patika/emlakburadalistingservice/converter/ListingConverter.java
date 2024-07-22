package com.patika.emlakburadalistingservice.converter;

import com.patika.emlakburadalistingservice.dto.request.ListingSaveRequest;
import com.patika.emlakburadalistingservice.dto.request.ListingUpdateRequest;
import com.patika.emlakburadalistingservice.dto.response.ListingDetailsResponse;
import com.patika.emlakburadalistingservice.dto.response.ListingResponse;
import com.patika.emlakburadalistingservice.model.Listing;
import com.patika.emlakburadalistingservice.model.enums.ListingStatus;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ListingConverter {
    public static Listing toListing(ListingSaveRequest listingSaveRequest, Long userId) {
        return Listing.builder()
                .title(listingSaveRequest.getTitle())
                .userId(userId)
                .listingStatus(ListingStatus.IN_REVIEW)
                .createDate(LocalDateTime.now())
                .expirationDate(LocalDateTime.now().plusDays(30))
                .price(listingSaveRequest.getPrice())
                .details(listingSaveRequest.getDetails())
                .address(listingSaveRequest.getAddress())
                .summary(listingSaveRequest.getSummary())
                .roomNumber(listingSaveRequest.getRoomNumber())
                .size(listingSaveRequest.getSize())
                .floor(listingSaveRequest.getFloor())
                .age(listingSaveRequest.getAge())
                .deposit(listingSaveRequest.getDeposit())
                .build();
    }
    public static Listing toUpdate(ListingUpdateRequest listingUpdateRequest, Listing listing) {
        Listing.ListingBuilder builder = listing.toBuilder();

        updateIfPresent(builder::title, listingUpdateRequest.getTitle());
        updateIfPresent(builder::address, listingUpdateRequest.getAddress());
        updateIfPresent(builder::summary, listingUpdateRequest.getSummary());
        updateIfPresent(builder::price, listingUpdateRequest.getPrice());
        updateIfPresent(builder::details, listingUpdateRequest.getDetails());
        updateIfPresent(builder::roomNumber, listingUpdateRequest.getRoomNumber());
        updateIfPresent(builder::size, listingUpdateRequest.getSize());
        updateIfPresent(builder::floor, listingUpdateRequest.getFloor());
        updateIfPresent(builder::age, listingUpdateRequest.getAge());
        updateIfPresent(builder::deposit, listingUpdateRequest.getDeposit());

        Optional.ofNullable(listingUpdateRequest.getListingStatus())
                .filter(status -> status != ListingStatus.IN_REVIEW)
                .ifPresent(builder::listingStatus);

        updateIfPresent(builder::expirationDate, listingUpdateRequest.getExpirationDate());

        return builder.build();
    }
    private static <T> void updateIfPresent(java.util.function.Consumer<T> setter, T value) {
        Optional.ofNullable(value).ifPresent(setter);
    }

    public static ListingResponse toResponse(Listing listing) {
        return ListingResponse.builder()
                .id(listing.getId())
                .title(listing.getTitle())
                .price(listing.getPrice())
                .listingStatus(listing.getListingStatus())
                .createDate(listing.getCreateDate())
                .expirationDate(listing.getExpirationDate())
                .build();
    }
    public static List<ListingResponse> toResponse(List<Listing> listings) {
        return listings
                .stream()
                .map(ListingConverter::toResponse)
                .collect(Collectors.toList());
    }

    public static ListingDetailsResponse toDetailsResponse(Optional<Listing> optionalListing) {
        if (optionalListing.isPresent()) {
            Listing listing = optionalListing.get();
            return ListingDetailsResponse.builder()
                    .title(listing.getTitle())
                    .listingStatus(listing.getListingStatus())
                    .price(listing.getPrice())
                    .details(listing.getDetails())
                    .address(listing.getAddress())
                    .summary(listing.getSummary())
                    .roomNumber(listing.getRoomNumber())
                    .size(listing.getSize())
                    .floor(listing.getFloor())
                    .age(listing.getAge())
                    .deposit(listing.getDeposit())
                    .build();
        } else {
            return null;
        }

    }
}
