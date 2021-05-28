package com.example.jabits_homework.service.crud;

import com.example.jabits_homework.entity.crud.CRUD;
import com.example.jabits_homework.entity.crud.repository.CRUDRepository;
import com.example.jabits_homework.entity.image.crud_image.CRUDImage;
import com.example.jabits_homework.entity.image.crud_image.repository.CRUDImageRepository;
import com.example.jabits_homework.entity.user.User;
import com.example.jabits_homework.entity.user.enums.Authority;
import com.example.jabits_homework.entity.user.repository.UserRepository;
import com.example.jabits_homework.jwt.JwtProvider;
import com.example.jabits_homework.payload.request.UpdateRequest;
import com.example.jabits_homework.payload.request.WriteRequest;
import com.example.jabits_homework.payload.response.CRUDResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class CRUDServiceImpl implements CRUDService {

    private final static int MAX_POST_PAGE = 5;

    private final CRUDRepository crudRepository;
    private final CRUDImageRepository crudImageRepository;
    private final UserRepository userRepository;

    private final JwtProvider jwtProvider;

    @Value("${image.dir}")
    private String imageDir;

    private <T> void setIfNotNull(Consumer<T> setter, T value) {
        if(value != null)
            setter.accept(value);
    }

    @Override
    public List<CRUDResponse> getCRUD(String token, Integer pageNum) {
        userRepository.findByUserId(jwtProvider.getUserId(token))
                .orElseThrow(RuntimeException::new);

        List<CRUDResponse> responses = new ArrayList<>();
        Page<CRUD> cruds = crudRepository.findAll(PageRequest.of(
                pageNum-1,
                MAX_POST_PAGE
        ));

        for(CRUD crud : cruds) {
            CRUDImage crudImage = crudImageRepository.findByListId(crud.getListId())
                    .orElseThrow(RuntimeException::new);

            CRUDResponse crudResponse = CRUDResponse.builder()
                    .listId(crud.getListId())
                    .title(crud.getTitle())
                    .content(crud.getContent())
                    .imageName(crudImage.getImageName())
                    .build();

            responses.add(crudResponse);
        }

        return responses;
    }

    @SneakyThrows
    @Override
    public void writeCRUD(String token, WriteRequest writeRequest) {
        User user = userRepository.findByUserId(jwtProvider.getUserId(token))
                .orElseThrow(RuntimeException::new);

        if(user.getAuthority() != Authority.ADMIN)
            throw new RuntimeException();

        CRUD crud = crudRepository.save(
                CRUD.builder()
                        .title(writeRequest.getTitle())
                        .content(writeRequest.getContent())
                        .build()
        );

        if(writeRequest.getImage().isEmpty()) {
            crudImageRepository.save(
                    CRUDImage.builder()
                    .listId(crud.getListId())
                    .imageName("")
                    .build()
            );
        }else {
            if(writeRequest.getImage().getOriginalFilename() == null || writeRequest.getImage().getOriginalFilename().length() == 0)
                throw new RuntimeException();

            int idx = writeRequest.getImage().getOriginalFilename().indexOf(".");
            String ex = writeRequest.getImage().getOriginalFilename().substring(idx + 1);

            if(!(ex.contains("jpg") || ex.contains("png") || ex.contains("jpeg") || ex.contains("gif")))
                throw new RuntimeException();

            String imageName = UUID.randomUUID().toString() + "." + ex;

            crudImageRepository.save(
                    CRUDImage.builder()
                    .listId(crud.getListId())
                    .imageName(imageName)
                    .build()
            );

            writeRequest.getImage().transferTo(new File(imageDir, imageName));
        }
    }

    @Override
    public void updateCRUD(String token, Long crudId, UpdateRequest updateRequest) {
        User user = userRepository.findByUserId(jwtProvider.getUserId(token))
                .orElseThrow(RuntimeException::new);

        if(user.getAuthority() != Authority.ADMIN)
            throw new RuntimeException();

        CRUD crud = crudRepository.findByListId(crudId)
                .orElseThrow(RuntimeException::new);

        setIfNotNull(crud::setTitle, updateRequest.getTitle());
        setIfNotNull(crud::setContent, updateRequest.getContent());

        crudRepository.save(crud);
    }

    @SneakyThrows
    @Override
    public void updateCRUDImage(String token, Long crudId, MultipartFile image) {
        User user = userRepository.findByUserId(jwtProvider.getUserId(token))
                .orElseThrow(RuntimeException::new);

        if(user.getAuthority() != Authority.ADMIN)
            throw new RuntimeException();

        CRUDImage crudImage = crudImageRepository.findByListId(crudId)
                .orElseThrow(RuntimeException::new);

        File file = new File(imageDir, crudImage.getImageName());

        if(file.exists()) file.delete();

        if(image.isEmpty()) {
            crudImage.updateImageName("");
        }else {
            if(image.getOriginalFilename() == null || image.getOriginalFilename().length() == 0)
                throw new RuntimeException();

            int idx = image.getOriginalFilename().indexOf(".");
            String ex = image.getOriginalFilename().substring(idx + 1);

            if(!(ex.contains("jpg") || ex.contains("png") || ex.contains("jpeg") || ex.contains("gif")))
                throw new RuntimeException();

            String imageName = UUID.randomUUID().toString() + "." + ex;

            crudImage.updateImageName(imageName);

            image.transferTo(new File(imageDir, imageName));
        }

        crudImageRepository.save(crudImage);
    }

    @Override
    public void deleteCRUD(String token, Long crudId) {
        User user = userRepository.findByUserId(jwtProvider.getUserId(token))
                .orElseThrow(RuntimeException::new);

        if(user.getAuthority() != Authority.ADMIN)
            throw new RuntimeException();

        CRUD crud = crudRepository.findByListId(crudId)
                .orElseThrow(RuntimeException::new);

        CRUDImage crudImage = crudImageRepository.findByListId(crud.getListId())
                .orElseThrow(RuntimeException::new);

        File file = new File(imageDir, crudImage.getImageName());

        if(file.exists()) file.delete();

        crudRepository.deleteByListId(crud.getListId());
        crudImageRepository.deleteByListId(crud.getListId());
    }

    @SneakyThrows
    @Override
    public byte[] getImage(String imageName) {
        crudImageRepository.findByImageName(imageName)
                .orElseThrow(RuntimeException::new);

        File file = new File(imageDir, imageName);
        if(!file.exists()) throw new RuntimeException();

        InputStream inputStream = new FileInputStream(file);

        return IOUtils.toByteArray(inputStream);
    }
}