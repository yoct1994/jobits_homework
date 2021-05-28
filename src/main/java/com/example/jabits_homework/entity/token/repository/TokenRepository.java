package com.example.jabits_homework.entity.token.repository;

import com.example.jabits_homework.entity.token.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    Optional<Token> findByRefreshToken(String refreshToken);
    void deleteByRefreshToken(String refreshToken);
}
