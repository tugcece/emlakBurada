package com.patika.emlakburadauserservice.controller;

import com.patika.emlakburadauserservice.dto.request.UserSaveRequest;
import com.patika.emlakburadauserservice.dto.response.UserResponse;
import com.patika.emlakburadauserservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void createUser() throws Exception {
        UserSaveRequest request = new UserSaveRequest();
        request.setName("Test");
        request.setLastName("User");
        request.setEmail("test@example.com");
        request.setPassword("password");
        request.setPhone("123456789");
        request.setAddress("Test Address");

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Test\",\"lastName\":\"User\",\"email\":\"test@example.com\",\"password\":\"password\",\"phone\":\"123456789\",\"address\":\"Test Address\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    void getById() throws Exception {
        Long userId = 1L;
        UserResponse userResponse = new UserResponse();
        userResponse.setId(userId);
        userResponse.setName("Test");
        userResponse.setLastName("User");
        userResponse.setEmail("test@example.com");
        userResponse.setPhone("123456789");
        userResponse.setAddress("Test Address");

        Mockito.when(userService.getById(anyLong())).thenReturn(userResponse);

        mockMvc.perform(get("/api/v1/users/id/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(userId))
                .andExpect(jsonPath("$.data.name").value("Test"))
                .andExpect(jsonPath("$.data.lastName").value("User"))
                .andExpect(jsonPath("$.data.email").value("test@example.com"))
                .andExpect(jsonPath("$.data.phone").value("123456789"))
                .andExpect(jsonPath("$.data.address").value("Test Address"));
    }

    @Test
    void getUserByEmail() throws Exception {
        String email = "test@example.com";
        UserResponse userResponse = new UserResponse();
        userResponse.setId(1L);
        userResponse.setName("Test");
        userResponse.setLastName("User");
        userResponse.setEmail(email);
        userResponse.setPhone("123456789");
        userResponse.setAddress("Test Address");

        Mockito.when(userService.getByEmail(anyString())).thenReturn(userResponse);

        mockMvc.perform(get("/api/v1/users/email/{email}", email))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.name").value("Test"))
                .andExpect(jsonPath("$.data.lastName").value("User"))
                .andExpect(jsonPath("$.data.email").value(email))
                .andExpect(jsonPath("$.data.phone").value("123456789"))
                .andExpect(jsonPath("$.data.address").value("Test Address"));
    }

    @Test
    void uploadImage() throws Exception {
        Long userId = 1L;
        String responseMessage = "Image uploaded successfully";

        Mockito.when(userService.uploadImage(any(MultipartFile.class), anyLong())).thenReturn(responseMessage);

        mockMvc.perform(multipart("/api/v1/users/image/{userId}", userId)
                        .file("image", "test-image".getBytes(StandardCharsets.UTF_8)))
                .andExpect(status().isOk())
                .andExpect(content().string(responseMessage));
    }

    @Test
    void getImageByListingId() throws Exception {
        Long userId = 1L;
        byte[] imageData = "test-image".getBytes(StandardCharsets.UTF_8);

        Mockito.when(userService.getImageByUserId(anyLong())).thenReturn(imageData);

        mockMvc.perform(get("/api/v1/users/image/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", "attachment; filename=\"photo.jpg\""))
                .andExpect(content().contentType(MediaType.IMAGE_JPEG))
                .andExpect(content().bytes(imageData));
    }
}
