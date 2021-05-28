package com.example.jabits_homework.service.auth;

import com.example.jabits_homework.payload.request.SignInRequest;
import com.example.jabits_homework.payload.response.TokenResponse;

public interface AuthService {
    TokenResponse signIn(SignInRequest signInRequest);
    TokenResponse refreshToken(String refreshToken);
}
