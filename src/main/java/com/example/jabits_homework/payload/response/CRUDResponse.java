package com.example.jabits_homework.payload.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CRUDResponse {
    private Long listId;
    private String title;
    private String content;
    private String imageName;
}
