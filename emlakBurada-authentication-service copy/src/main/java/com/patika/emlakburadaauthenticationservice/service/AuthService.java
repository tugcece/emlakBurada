package com.patika.emlakburadaauthenticationservice.service;

import com.patika.emlakburadaauthenticationservice.dto.request.LoginRequest;
import com.patika.emlakburadaauthenticationservice.model.User;
import com.patika.emlakburadaauthenticationservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public User authenticate(LoginRequest input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );
        User user =userRepository.findByemail(input.getEmail());
        return user;
    }

}