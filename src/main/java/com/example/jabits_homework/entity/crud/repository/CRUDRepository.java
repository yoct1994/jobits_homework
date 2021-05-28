package com.example.jabits_homework.entity.crud.repository;

import com.example.jabits_homework.entity.crud.CRUD;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CRUDRepository extends JpaRepository<CRUD, Long> {
    Page<CRUD> findAll(Pageable pageable);
    Optional<CRUD> findByListId(Long listId);
    void deleteByListId(Long listId);
}
