package com.patika.emlakburadalistingstatusservice.consumer;

import com.patika.emlakburadalistingstatusservice.model.Listing;
import com.patika.emlakburadalistingstatusservice.model.enums.ListingStatus;
import com.patika.emlakburadalistingstatusservice.repository.ListingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ListingConsumer {

    private final ListingRepository listingRepository;
    /* This service is for consuming RabbitMq queues produced by other services.
     * When the message is captured here, the status of the related listing becomes
     * ACTIVE.
     */
    @RabbitListener(queues = "${listing.queue}")
    public void receiveListing(Listing listing) {
        Optional<Listing> optionalListing = listingRepository.findById(listing.getId());
        if (optionalListing.isPresent()) {
            Listing foundedListing = optionalListing.get();
            foundedListing.setListingStatus(ListingStatus.ACTIVE);
            listingRepository.save(foundedListing);
        }
    }
}
