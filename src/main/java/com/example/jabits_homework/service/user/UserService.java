package com.example.jabits_homework.service.user;

import com.example.jabits_homework.payload.request.SignUpRequest;

public interface UserService {
    void signUp(SignUpRequest signUpRequest);
    void makeAdmin();
}
