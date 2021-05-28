package com.example.jabits_homework.payload.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class SignUpRequest {
    @NotBlank
    private String id;
    @NotBlank
    private String password;
}
