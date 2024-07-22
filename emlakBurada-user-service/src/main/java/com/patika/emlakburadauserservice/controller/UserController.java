package com.patika.emlakburadauserservice.controller;

import com.patika.emlakburadauserservice.dto.request.UserSaveRequest;
import com.patika.emlakburadauserservice.dto.response.GenericResponse;
import com.patika.emlakburadauserservice.dto.response.UserResponse;
import com.patika.emlakburadauserservice.model.User;
import com.patika.emlakburadauserservice.service.UserService;
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
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserSaveRequest request) {
        userService.save(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/id/{id}")
    public GenericResponse<UserResponse> getById(@PathVariable("id") Long userId) {
        UserResponse userResponse = userService.getById(userId);
        return GenericResponse.success(userResponse);
    }

    @GetMapping("/email/{email}")
    public GenericResponse<UserResponse>  getUserByEmail(@PathVariable("email") String email){
        UserResponse userResponse = userService.getByEmail(email);
        return GenericResponse.success(userResponse);
    }
    @PostMapping("/image/{userId}")
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile file,
                                              @PathVariable("userId") Long userId) {
        try {
            String response = userService.uploadImage(file, userId);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image");
        }
    }

    @GetMapping("/image/{userId}")
    public ResponseEntity<byte[]> getImageByListingId(@PathVariable("userId") Long userId) {
        byte[] imageData = userService.getImageByUserId(userId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"photo.jpg\"")
                .contentType(MediaType.IMAGE_JPEG)
                .body(imageData);
    }
}

