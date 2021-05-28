package com.example.jabits_homework.controller;

import com.example.jabits_homework.payload.request.SignInRequest;
import com.example.jabits_homework.payload.response.TokenResponse;
import com.example.jabits_homework.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public TokenResponse signIn(@RequestBody @Valid SignInRequest signInRequest) {
        return authService.signIn(signInRequest);
    }

    @PutMapping("/refresh")
    public TokenResponse refreshToken(@RequestHeader("X-Refresh-Token") @Valid String refreshToken) {
        return authService.refreshToken(refreshToken);
    }
}
