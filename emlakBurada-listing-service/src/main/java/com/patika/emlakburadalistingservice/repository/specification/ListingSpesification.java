package com.patika.emlakburadalistingservice.repository.specification;


import com.patika.emlakburadalistingservice.dto.request.ListingSearchRequest;
import com.patika.emlakburadalistingservice.model.Listing;
import jakarta.persistence.criteria.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ListingSpesification {

    public static Specification<Listing> initListingSpesification (ListingSearchRequest request){
        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicateList = new ArrayList<>();

            if (request.getUserId() != null) {
                predicateList.add(criteriaBuilder.equal(root.get("userId"), request.getUserId()));
            }

            if (request.getListingStatus() != null) {
                predicateList.add(criteriaBuilder.equal(root.get("listingStatus"), request.getListingStatus()));
            }

            return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
        };
    }
}
