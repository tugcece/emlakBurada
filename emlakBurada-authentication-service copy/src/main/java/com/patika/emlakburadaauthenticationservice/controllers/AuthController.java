package com.patika.emlakburadaauthenticationservice.controllers;

import com.patika.emlakburadaauthenticationservice.config.auth.JwtTokenProvider;
import com.patika.emlakburadaauthenticationservice.dto.request.LoginRequest;
import com.patika.emlakburadaauthenticationservice.dto.response.LoginResponse;
import com.patika.emlakburadaauthenticationservice.model.User;
import com.patika.emlakburadaauthenticationservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
  private final JwtTokenProvider jwtTokenProvider;
  private final AuthService authenticationService;

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginRequest loginRequest) {
    User authenticatedUser = authenticationService.authenticate(loginRequest);
    String jwtToken = jwtTokenProvider.generateToken(authenticatedUser);
    LoginResponse loginResponse = new LoginResponse(authenticatedUser.getId(),jwtToken,jwtTokenProvider.getExpirationTime());
    return ResponseEntity.ok(loginResponse);
  }
}