package com.example.jabits_homework.controller;

import com.example.jabits_homework.payload.request.SignUpRequest;
import com.example.jabits_homework.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/api/register")
    public void signUp(@RequestBody @Valid SignUpRequest signUpRequest) {
        userService.signUp(signUpRequest);
    }

    //기능 테스트를 위해 넣은 어드민 계정 만들기
    @PostMapping("/api/make")
    public void makeAdmin() {
        userService.makeAdmin();
    }
}
