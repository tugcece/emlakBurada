package com.patika.emlakburadalistingservice.service;

import com.patika.emlakburadalistingservice.client.log.service.LogService;
import com.patika.emlakburadalistingservice.client.user.dto.response.UserResponse;
import com.patika.emlakburadalistingservice.client.user.service.UserService;
import com.patika.emlakburadalistingservice.client.userBalance.dto.response.UserBalanceResponse;
import com.patika.emlakburadalistingservice.client.userBalance.service.UserBalanceService;
import com.patika.emlakburadalistingservice.converter.ListingConverter;
import com.patika.emlakburadalistingservice.dto.PageWrapper;
import com.patika.emlakburadalistingservice.dto.request.ListingSaveRequest;
import com.patika.emlakburadalistingservice.dto.request.ListingSearchRequest;
import com.patika.emlakburadalistingservice.dto.request.ListingUpdateRequest;
import com.patika.emlakburadalistingservice.dto.response.ListingDetailsResponse;
import com.patika.emlakburadalistingservice.dto.response.ListingResponse;
import com.patika.emlakburadalistingservice.exception.EmlakBuradaException;
import com.patika.emlakburadalistingservice.exception.ExceptionMessages;
import com.patika.emlakburadalistingservice.model.Listing;
import com.patika.emlakburadalistingservice.model.Photo;
import com.patika.emlakburadalistingservice.producer.ListingProducer;
import com.patika.emlakburadalistingservice.repository.ListingRepository;
import com.patika.emlakburadalistingservice.repository.PhotoRepository;
import com.patika.emlakburadalistingservice.repository.specification.ListingSpesification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j

public class ListingService {

    private final ListingRepository listingRepository;
    private final ListingProducer listingProducer;
    private final UserService userService;
    private final UserBalanceService userBalanceService;
    private final PhotoRepository photoRepository;
    private final LogService logService;


    /* First of all, we communicate with User and UserBalance services and provide
     * the necessary controls. Then we create Listing with Converter. We save the
     * Listing we created to the database and make the balance settings of the async
     * user via RabbitMQ.
     */
    @Transactional
    public Listing save(ListingSaveRequest request, Long userId) {

        UserResponse response = userService.getUserById(userId);
        if (response == null){
            logService.log("DbLogStrategy",ExceptionMessages.USER_NOT_FOUND);
            throw new EmlakBuradaException(ExceptionMessages.USER_NOT_FOUND);
        }

      UserBalanceResponse userBalanceResponse = userBalanceService.getBalanceAndExpireDateByUserId(userId);
        if (userBalanceResponse == null || userBalanceResponse.getRemainingListings() == 0 || userBalanceResponse.getValidUntil().isBefore(LocalDateTime.now())){
            logService.log("DbLogStrategy",ExceptionMessages.BALANCE_ERROR);
            throw new EmlakBuradaException(ExceptionMessages.BALANCE_ERROR);
        }
        Listing listing = ListingConverter.toListing(request, userId);
        listingRepository.save(listing);
        userBalanceService.reduceBalance(userId);
        return listing;
    }
    @Transactional(readOnly = true)
    public ListingDetailsResponse findById(Long listingId) {
        Optional<Listing> listingOptional = listingRepository.findById(listingId);

        ListingDetailsResponse response = ListingConverter.toDetailsResponse(listingOptional);
        if(response == null){
            logService.log("DbLogStrategy",ExceptionMessages.LISTING_NOT_FOUND);
            log.error(ExceptionMessages.LISTING_NOT_FOUND);
            throw new EmlakBuradaException(ExceptionMessages.LISTING_NOT_FOUND);
        }
        return  response;
    }

    /* It is a method that brings all listings of the user or those according to a certain status
     * according to the incoming request. The relevant pagination values are also returned by wrapping
     *  with PageWrapper.
     */
    @Transactional(readOnly = true)
    public PageWrapper<ListingResponse> getUserListingsByStatus(ListingSearchRequest request) {
        Specification<Listing> listingSpecification = ListingSpesification.initListingSpesification(request);
        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize());
        Page<Listing> listings = listingRepository.findAll(listingSpecification, pageRequest);
        List<ListingResponse> listingResponse = ListingConverter.toResponse(listings.stream().toList());

        log.info("users list from db.");

        return new PageWrapper<>(
                listingResponse,
                listings.getTotalPages(),
                listings.getTotalElements(),
                listings.getNumber(),
                listings.getSize()
        );
    }
    @Transactional
    public void updateById(ListingUpdateRequest request, Long id) {
        if (!listingRepository.existsById(id)) {
            logService.log("DbLogStrategy",ExceptionMessages.LISTING_NOT_FOUND);
            log.error(ExceptionMessages.LISTING_NOT_FOUND);
            throw new EmlakBuradaException(ExceptionMessages.LISTING_NOT_FOUND);
        }
        Listing listing = listingRepository.findById(id).get();
        Listing convertListing = ListingConverter.toUpdate(request, listing);
        listingRepository.save(convertListing);
    }
    @Transactional
    public void deleteById(Long listingId) {
        if (listingRepository.findById(listingId).isEmpty()) {
            logService.log("DbLogStrategy",ExceptionMessages.LISTING_NOT_FOUND);
            log.error(ExceptionMessages.LISTING_NOT_FOUND);
            throw new EmlakBuradaException(ExceptionMessages.LISTING_NOT_FOUND);
        }
        if(photoRepository.findByListingId(listingId).isEmpty()){
            logService.log("DbLogStrategy",ExceptionMessages.PHOTO_NOT_FOUND);
            log.error(ExceptionMessages.PHOTO_NOT_FOUND);
            throw new EmlakBuradaException(ExceptionMessages.PHOTO_NOT_FOUND);
        }
        photoRepository.deleteByListingId(listingId);
        listingRepository.deleteById(listingId);
    }
    /* This method communicates with RabbitMQ and asynchronously
     * sets the listing status to ACTIVE.
     */
    @Transactional
    public void updateStatusActive(Listing listing) {
        if (listingRepository.findById(listing.getId()).isEmpty()) {
            logService.log("DbLogStrategy",ExceptionMessages.LISTING_NOT_FOUND);
            log.error(ExceptionMessages.LISTING_NOT_FOUND);
            throw new EmlakBuradaException(ExceptionMessages.LISTING_NOT_FOUND);
        }
        listingProducer.sendListingForActivated(listing);
    }


    @Transactional
    public String uploadImage(MultipartFile file, Long listingId) throws IOException {
        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new RuntimeException(ExceptionMessages.LISTING_NOT_FOUND));

        if (listing.getPhoto() != null) {
            photoRepository.delete(listing.getPhoto());
        }

        Photo photo = new Photo(file.getOriginalFilename(), file.getContentType(), file.getBytes(), listing);
        listing.setPhoto(photo);
        listingRepository.save(listing);
        return "Image uploaded successfully";
    }

    @Transactional(readOnly = true)
    public byte[] getImageByListingId(Long listingId) {
        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new RuntimeException(ExceptionMessages.LISTING_NOT_FOUND));
        Photo photo = listing.getPhoto();
        if (photo == null) {
            logService.log("DbLogStrategy",ExceptionMessages.PHOTO_NOT_FOUND);
            log.error(ExceptionMessages.PHOTO_NOT_FOUND);
            throw new RuntimeException(ExceptionMessages.PHOTO_NOT_FOUND);
        }
        return photo.getData();
    }

}
