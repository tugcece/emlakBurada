package com.patika.emlakburadaauthenticationservice.service;

import com.patika.emlakburadaauthenticationservice.dto.request.LoginRequest;
import com.patika.emlakburadaauthenticationservice.model.User;
import com.patika.emlakburadaauthenticationservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Test
    void authenticate_shouldReturnUser_whenCredentialsAreValid() {
        // given
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password");

        User user = new User();
        user.setEmail("test@example.com");

        Mockito.when(userRepository.findByemail(anyString())).thenReturn(user);

        // when
        User result = authService.authenticate(loginRequest);

        // then
        assertThat(result).isEqualTo(user);
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository, times(1)).findByemail(loginRequest.getEmail());
    }

    @Test
    void authenticate_shouldThrowException_whenCredentialsAreInvalid() {
        // given
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("invalid-password");

        Mockito.doThrow(new RuntimeException("Invalid credentials"))
                .when(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));

        // when
        Throwable exception = org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> {
            authService.authenticate(loginRequest);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("Invalid credentials");
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository, never()).findByemail(anyString());
    }
}
