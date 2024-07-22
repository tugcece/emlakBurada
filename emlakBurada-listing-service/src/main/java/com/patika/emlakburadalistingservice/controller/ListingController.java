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
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/listing")
public class ListingController {

    private final ListingService listingService;

    @PostMapping("")
    public ResponseEntity<Long> save(@RequestBody ListingSaveRequest request,
                                        @RequestHeader(value = "userId") Long userId) {
        Listing listing = listingService.save(request, userId);
        listingService.updateStatusActive(listing);
        return new ResponseEntity<>(listing.getId(), HttpStatus.CREATED);
    }

    @PostMapping("/image/{listingId}")
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile file,
                                              @PathVariable("listingId") Long listingId) {
        try {
            String response = listingService.uploadImage(file, listingId);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image");
        }
    }

    @GetMapping("/image/{listingId}")
    public ResponseEntity<byte[]> getImageByListingId(@PathVariable Long listingId) {
        byte[] imageData = listingService.getImageByListingId(listingId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"photo.jpg\"")
                .contentType(MediaType.IMAGE_JPEG)
                .body(imageData);
    }

    @GetMapping("id/{id}")
    public ResponseEntity<ListingDetailsResponse> getById(@PathVariable("id") Long id) {
        ListingDetailsResponse listing = listingService.findById(id);
        return new ResponseEntity<>(listing, HttpStatus.OK);
    }

    @GetMapping("/status")
    public ResponseEntity<GenericResponse<PageWrapper<ListingResponse>>> getListingsByStatus(@RequestBody ListingSearchRequest request) {
        return new ResponseEntity<>(GenericResponse.success(listingService.getUserListingsByStatus(request)), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateListing(@RequestBody ListingUpdateRequest request, @PathVariable("id") Long listingId) {
        listingService.updateById(request,listingId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteListing(@PathVariable("id") Long listingId) {
        listingService.deleteById(listingId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

