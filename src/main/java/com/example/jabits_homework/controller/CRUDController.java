package com.example.jabits_homework.controller;

import com.example.jabits_homework.payload.request.UpdateRequest;
import com.example.jabits_homework.payload.request.WriteRequest;
import com.example.jabits_homework.payload.response.CRUDResponse;
import com.example.jabits_homework.service.crud.CRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CRUDController {

    private final CRUDService crudService;

    @GetMapping("/crud/{pageNum}")
    public List<CRUDResponse> getCRUDList(@RequestHeader("Authorization") @Valid String token,
                                          @PathVariable @Valid Integer pageNum) {
        return crudService.getCRUD(token, pageNum);
    }

    @GetMapping(value = "/crud/image/{imageName}",
            produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE})
    public byte[] getImage(@PathVariable String imageName) {
        return crudService.getImage(imageName);
    }

    @PostMapping("/crud")
    public void writeList(@RequestHeader("Authorization") @Valid String token,
                          @RequestParam @Valid String title,
                          @RequestParam @Valid String content,
                          @RequestParam MultipartFile image) {
        crudService.writeCRUD(
                token,
                WriteRequest.builder()
                        .title(title)
                        .content(content)
                        .image(image)
                        .build()
        );
    }

    @PutMapping("/crud/{listId}")
    public void updateCRUD(@RequestHeader("Authorization") String token,
                           @PathVariable Long listId,
                           @RequestBody @Valid UpdateRequest updateRequest) {
        crudService.updateCRUD(token, listId, updateRequest);
    }

    @PutMapping("/crud/image/{listId}")
    public void updateCRUD(@RequestHeader("Authorization") String token,
                           @PathVariable Long listId,
                           @RequestParam MultipartFile image) {
        crudService.updateCRUDImage(token, listId, image);
    }

    @DeleteMapping("/crud/{listId}")
    public void deleteCRUD(@RequestHeader("Authorization") String token,
                           @PathVariable Long listId) {
        crudService.deleteCRUD(token, listId);
    }
}
