package com.patika.emlakburadalistingservice.controller;

import com.patika.emlakburadalistingservice.dto.PageWrapper;
import com.patika.emlakburadalistingservice.dto.request.ListingSaveRequest;
import com.patika.emlakburadalistingservice.dto.request.ListingSearchRequest;
import com.patika.emlakburadalistingservice.dto.request.ListingUpdateRequest;
import com.patika.emlakburadalistingservice.dto.response.GenericResponse;
import com.patika.emlakburadalistingservice.dto.response.ListingDetailsResponse;
import com.patika.emlakburadalistingservice.dto.response.ListingResponse;
import com.patika.emlakburadalistingservice.model.Listing;
import com.patika.emlakburadalistingservice.service.ListingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListingControllerTest {

    @Mock
    private ListingService listingService;

    @InjectMocks
    private ListingController listingController;

    private ListingSaveRequest listingSaveRequest;
    private ListingUpdateRequest listingUpdateRequest;
    private Listing listing;
    private MultipartFile file;

    @BeforeEach
    void setUp() {
        listingSaveRequest = new ListingSaveRequest();
        listingSaveRequest.setTitle("Test Listing");
        listingSaveRequest.setPrice(1000.0);

        listingUpdateRequest = new ListingUpdateRequest();
        listingUpdateRequest.setTitle("Updated Listing");
        listingUpdateRequest.setPrice(1500.0);

        listing = new Listing();
        listing.setId(1L);
        listing.setTitle("Test Listing");
        listing.setPrice(1000.0);
    }

    @Test
    void testSave() {
        Long userId = 1L;
        when(listingService.save(any(ListingSaveRequest.class), eq(userId))).thenReturn(listing);

        ResponseEntity<Long> response = listingController.save(listingSaveRequest, userId);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(listing.getId(), response.getBody());
    }

    @Test
    void testUploadImage() throws IOException {
        Long listingId = 1L;

        MockMultipartFile mockFile = new MockMultipartFile(
                "image",
                "test.jpg",
                "image/jpeg",
                new byte[]{1, 2, 3}
        );

        when(listingService.uploadImage(mockFile, listingId)).thenReturn("Image uploaded successfully");

        ResponseEntity<String> response = listingController.uploadImage(mockFile, listingId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Image uploaded successfully", response.getBody());
    }

    @Test
    void testUploadImageFailure() throws IOException {
        Long listingId = 1L;
        when(listingService.uploadImage(file, listingId)).thenThrow(IOException.class);

        ResponseEntity<String> response = listingController.uploadImage(file, listingId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Failed to upload image", response.getBody());
    }

    @Test
    void testGetImageByListingId() {
        Long listingId = 1L;
        byte[] imageData = new byte[]{1, 2, 3};
        when(listingService.getImageByListingId(listingId)).thenReturn(imageData);

        ResponseEntity<byte[]> response = listingController.getImageByListingId(listingId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertArrayEquals(imageData, response.getBody());
        assertEquals("attachment; filename=\"photo.jpg\"", response.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION));
        assertEquals(MediaType.IMAGE_JPEG, response.getHeaders().getContentType());
    }

    @Test
    void testGetById() {
        Long listingId = 1L;
        ListingDetailsResponse listingDetailsResponse = new ListingDetailsResponse();
        listingDetailsResponse.setTitle("Test Listing");
        listingDetailsResponse.setPrice(1000.0);

        when(listingService.findById(listingId)).thenReturn(listingDetailsResponse);

        ResponseEntity<ListingDetailsResponse> response = listingController.getById(listingId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(listingDetailsResponse, response.getBody());
    }

    @Test
    void testGetAllListings() {
        ListingSearchRequest request = new ListingSearchRequest();
        PageWrapper<ListingResponse> pageWrapper = new PageWrapper<>(Collections.emptyList(), 1, 1L, 0, 10);

        when(listingService.getUserListingsByStatus(request)).thenReturn(pageWrapper);

        ResponseEntity<GenericResponse<PageWrapper<ListingResponse>>> response = listingController.getListingsByStatus(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pageWrapper, response.getBody().getData());
    }

    @Test
    void testGetListingsByStatus() {
        ListingSearchRequest request = new ListingSearchRequest();
        PageWrapper<ListingResponse> pageWrapper = new PageWrapper<>(Collections.emptyList(), 1, 1L, 0, 10);

        when(listingService.getUserListingsByStatus(request)).thenReturn(pageWrapper);

        ResponseEntity<GenericResponse<PageWrapper<ListingResponse>>> response = listingController.getListingsByStatus(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pageWrapper, response.getBody().getData());
    }

    @Test
    void testUpdateListing() {
        Long listingId = 1L;

        doNothing().when(listingService).updateById(any(ListingUpdateRequest.class), eq(listingId));

        ResponseEntity<Void> response = listingController.updateListing(listingUpdateRequest, listingId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testDeleteListing() {
        Long listingId = 1L;

        doNothing().when(listingService).deleteById(listingId);

        ResponseEntity<Void> response = listingController.deleteListing(listingId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
