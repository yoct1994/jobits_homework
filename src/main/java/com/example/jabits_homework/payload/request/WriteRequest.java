package com.example.jabits_homework.payload.request;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
public class WriteRequest {
    private String title;
    private String content;
    private MultipartFile image;
}
