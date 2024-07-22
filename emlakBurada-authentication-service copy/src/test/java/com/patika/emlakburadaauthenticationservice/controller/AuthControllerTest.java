package com.patika.emlakburadaauthenticationservice.controllers;

import com.patika.emlakburadaauthenticationservice.config.auth.JwtTokenProvider;
import com.patika.emlakburadaauthenticationservice.dto.request.LoginRequest;
import com.patika.emlakburadaauthenticationservice.exceptions.GlobalExceptionHandler;
import com.patika.emlakburadaauthenticationservice.model.User;
import com.patika.emlakburadaauthenticationservice.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void authenticate_shouldReturnLoginResponse_whenCredentialsAreValid() throws Exception {
        // given
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password");

        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        String jwtToken = "mocked-jwt-token";
        Long expirationTime = 3600L;

        when(authService.authenticate(any(LoginRequest.class))).thenReturn(user);
        when(jwtTokenProvider.generateToken(user)).thenReturn(jwtToken);
        when(jwtTokenProvider.getExpirationTime()).thenReturn(expirationTime);

        // when & then
        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"test@example.com\",\"password\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.token").value(jwtToken))
                .andExpect(jsonPath("$.expiresIn").value(expirationTime));
    }

    @Test
    void authenticate_shouldReturnUnauthorized_whenCredentialsAreInvalid() throws Exception {
        // given
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("invalid@example.com");
        loginRequest.setPassword("invalid-password");

        when(authService.authenticate(any(LoginRequest.class))).thenThrow(new BadCredentialsException("Invalid credentials"));

        // when & then
        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"invalid@example.com\",\"password\":\"invalid-password\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.errors[0]").value("Invalid credentials"));
    }
}
