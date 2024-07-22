package com.patika.emlakburadalistingservice.service;

import com.patika.emlakburadalistingservice.client.log.LogClient;
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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListingServiceTest {

    @InjectMocks
    private ListingService listingService;

    @Mock
    private ListingRepository listingRepository;

    @Mock
    private ListingProducer listingProducer;

    @Mock
    private UserService userService;

    @Mock
    private UserBalanceService userBalanceService;

    @Mock
    private PhotoRepository photoRepository;

    @Mock
    private LogClient logClient;

    @Test
    void save_shouldSaveListingWhenUserAndBalanceAreValid() {
        // given
        ListingSaveRequest request = new ListingSaveRequest();
        Long userId = 1L;
        UserResponse userResponse = new UserResponse();
        userResponse.setId(userId);
        UserBalanceResponse userBalanceResponse = new UserBalanceResponse();
        userBalanceResponse.setRemainingListings(1);
        userBalanceResponse.setValidUntil(LocalDateTime.now().plusDays(1));

        when(userService.getUserById(userId)).thenReturn(userResponse);
        when(userBalanceService.getBalanceAndExpireDateByUserId(userId)).thenReturn(userBalanceResponse);
        when(listingRepository.save(any(Listing.class))).thenReturn(new Listing());

        // when
        Listing listing = listingService.save(request, userId);

        // then
        assertThat(listing).isNotNull();
        verify(listingRepository, times(1)).save(any(Listing.class));
        verify(userBalanceService, times(1)).reduceBalance(userId);
        verify(logClient, never()).log(anyString(), anyString());
    }

    @Test
    void save_shouldThrowExceptionWhenUserNotFound() {
        // given
        ListingSaveRequest request = new ListingSaveRequest();
        Long userId = 1L;

        when(userService.getUserById(userId)).thenReturn(null);

        // when
        EmlakBuradaException exception = assertThrows(EmlakBuradaException.class, () -> listingService.save(request, userId));

        // then
        assertThat(exception.getMessage()).isEqualTo(ExceptionMessages.USER_NOT_FOUND);
        verify(logClient, times(1)).log("DbLogStrategy", ExceptionMessages.USER_NOT_FOUND);
    }

    @Test
    void save_shouldThrowExceptionWhenBalanceIsNotValid() {
        // given
        ListingSaveRequest request = new ListingSaveRequest();
        Long userId = 1L;
        UserResponse userResponse = new UserResponse();
        userResponse.setId(userId);

        when(userService.getUserById(userId)).thenReturn(userResponse);
        when(userBalanceService.getBalanceAndExpireDateByUserId(userId)).thenReturn(null);

        // when
        EmlakBuradaException exception = assertThrows(EmlakBuradaException.class, () -> listingService.save(request, userId));

        // then
        assertThat(exception.getMessage()).isEqualTo(ExceptionMessages.BALANCE_ERROR);
        verify(logClient, times(1)).log("DbLogStrategy", ExceptionMessages.BALANCE_ERROR);
    }

    @Test
    void findById_shouldReturnListingDetailsWhenListingExists() {
        // given
        Long listingId = 1L;
        Listing listing = new Listing();
        listing.setId(listingId);

        when(listingRepository.findById(listingId)).thenReturn(Optional.of(listing));

        // when
        ListingDetailsResponse response = listingService.findById(listingId);

        // then
        assertThat(response).isNotNull();
        verify(logClient, never()).log(anyString(), anyString());
    }

    @Test
    void findById_shouldThrowExceptionWhenListingNotFound() {
        // given
        Long listingId = 1L;

        when(listingRepository.findById(listingId)).thenReturn(Optional.empty());

        // when
        EmlakBuradaException exception = assertThrows(EmlakBuradaException.class, () -> listingService.findById(listingId));

        // then
        assertThat(exception.getMessage()).isEqualTo(ExceptionMessages.LISTING_NOT_FOUND);
        verify(logClient, times(1)).log("DbLogStrategy", ExceptionMessages.LISTING_NOT_FOUND);
    }

    @Test
    void getUserListingsByStatus_shouldReturnListings() {
        // given
        ListingSearchRequest request = new ListingSearchRequest();
        request.setPage(0);
        request.setSize(10);
        PageRequest pageRequest = PageRequest.of(0, 10);
        Listing listing = new Listing();
        Page<Listing> listings = new PageImpl<>(Collections.singletonList(listing));

        when(listingRepository.findAll(any(Specification.class), eq(pageRequest))).thenReturn(listings);

        // when
        PageWrapper<ListingResponse> response = listingService.getUserListingsByStatus(request);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getContent()).hasSize(1);
    }

    @Test
    void updateById_shouldUpdateListingWhenExists() {
        // given
        ListingUpdateRequest request = new ListingUpdateRequest();
        Long listingId = 1L;
        Listing listing = new Listing();
        listing.setId(listingId);

        when(listingRepository.existsById(listingId)).thenReturn(true);
        when(listingRepository.findById(listingId)).thenReturn(Optional.of(listing));
        when(listingRepository.save(any(Listing.class))).thenReturn(listing);

        // when
        listingService.updateById(request, listingId);

        // then
        verify(listingRepository, times(1)).save(any(Listing.class));
        verify(logClient, never()).log(anyString(), anyString());
    }

    @Test
    void updateById_shouldThrowExceptionWhenListingNotFound() {
        // given
        ListingUpdateRequest request = new ListingUpdateRequest();
        Long listingId = 1L;

        when(listingRepository.existsById(listingId)).thenReturn(false);

        // when
        EmlakBuradaException exception = assertThrows(EmlakBuradaException.class, () -> listingService.updateById(request, listingId));

        // then
        assertThat(exception.getMessage()).isEqualTo(ExceptionMessages.LISTING_NOT_FOUND);
        verify(logClient, times(1)).log("DbLogStrategy", ExceptionMessages.LISTING_NOT_FOUND);
    }

    @Test
    void deleteById_shouldDeleteListingWhenExists() {
        // given
        Long listingId = 1L;
        Listing listing = new Listing();
        listing.setId(listingId);

        when(listingRepository.findById(listingId)).thenReturn(Optional.of(listing));

        // when
        listingService.deleteById(listingId);

        // then
        verify(listingRepository, times(1)).deleteById(listingId);
        verify(logClient, never()).log(anyString(), anyString());
    }

    @Test
    void deleteById_shouldThrowExceptionWhenListingNotFound() {
        // given
        Long listingId = 1L;

        when(listingRepository.findById(listingId)).thenReturn(Optional.empty());

        // when
        EmlakBuradaException exception = assertThrows(EmlakBuradaException.class, () -> listingService.deleteById(listingId));

        // then
        assertThat(exception.getMessage()).isEqualTo(ExceptionMessages.LISTING_NOT_FOUND);
        verify(logClient, times(1)).log("DbLogStrategy", ExceptionMessages.LISTING_NOT_FOUND);
    }

    @Test
    void uploadImage_shouldUploadImageSuccessfully() throws IOException {
        // given
        Long listingId = 1L;
        MultipartFile file = mock(MultipartFile.class);
        Listing listing = new Listing();
        listing.setId(listingId);
        Photo existingPhoto = new Photo("oldFilename", "image/jpeg", new byte[0], null);
        listing.setPhoto(existingPhoto);

        when(listingRepository.findById(listingId)).thenReturn(Optional.of(listing));
        when(file.getOriginalFilename()).thenReturn("filename");
        when(file.getContentType()).thenReturn("image/jpeg");
        when(file.getBytes()).thenReturn(new byte[0]);

        // when
        String result = listingService.uploadImage(file, listingId);

        // then
        assertThat(result).isEqualTo("Image uploaded successfully");
        verify(photoRepository, times(1)).delete(existingPhoto);
        verify(listingRepository, times(1)).save(listing);
    }

    @Test
    void getImageByListingId_shouldReturnImageDataWhenPhotoExists() {
        // given
        Long listingId = 1L;
        Listing listing = new Listing();
        listing.setId(listingId);
        Photo photo = new Photo();
        photo.setData(new byte[0]);
        listing.setPhoto(photo);

        when(listingRepository.findById(listingId)).thenReturn(Optional.of(listing));

        // when
        byte[] result = listingService.getImageByListingId(listingId);

        // then
        assertThat(result).isEqualTo(photo.getData());
    }

    @Test
    void getImageByListingId_shouldThrowExceptionWhenPhotoNotFound() {
        // given
        Long listingId = 1L;
        Listing listing = new Listing();
        listing.setId(listingId);

        when(listingRepository.findById(listingId)).thenReturn(Optional.of(listing));

        // when
        RuntimeException exception = assertThrows(RuntimeException.class, () -> listingService.getImageByListingId(listingId));

        // then
        assertThat(exception.getMessage()).isEqualTo(ExceptionMessages.PHOTO_NOT_FOUND);
        verify(logClient, times(1)).log("DbLogStrategy", ExceptionMessages.PHOTO_NOT_FOUND);
    }
}
