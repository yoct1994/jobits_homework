package com.example.jabits_homework.entity.image.crud_image.repository;

import com.example.jabits_homework.entity.image.crud_image.CRUDImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CRUDImageRepository extends JpaRepository<CRUDImage, Long> {
    Optional<CRUDImage> findByListId(Long listId);
    void deleteByListId(Long listId);
    Optional<CRUDImage> findByImageName(String imageName);
}
