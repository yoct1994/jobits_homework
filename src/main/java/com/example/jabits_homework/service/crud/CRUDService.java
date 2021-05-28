package com.example.jabits_homework.service.crud;

import com.example.jabits_homework.payload.request.UpdateRequest;
import com.example.jabits_homework.payload.request.WriteRequest;
import com.example.jabits_homework.payload.response.CRUDResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CRUDService {
    List<CRUDResponse> getCRUD(String token, Integer pageNum);
    void writeCRUD(String token, WriteRequest writeRequest);
    void updateCRUD(String token, Long crudId, UpdateRequest updateRequest);
    void updateCRUDImage(String token, Long crudId, MultipartFile image);
    void deleteCRUD(String token, Long crudId);
    byte[] getImage(String imageName);
}
