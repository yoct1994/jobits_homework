package com.example.jabits_homework.entity.user.repository;

import com.example.jabits_homework.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findById(String id);
    Optional<User> findByUserId(Integer userId);
}
