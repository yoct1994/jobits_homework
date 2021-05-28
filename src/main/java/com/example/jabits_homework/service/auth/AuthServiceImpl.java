package com.example.jabits_homework.service.auth;

import com.example.jabits_homework.entity.token.Token;
import com.example.jabits_homework.entity.token.repository.TokenRepository;
import com.example.jabits_homework.entity.user.User;
import com.example.jabits_homework.entity.user.repository.UserRepository;
import com.example.jabits_homework.error.exceptions.LoginFailedException;
import com.example.jabits_homework.error.exceptions.RefreshTokenNotFoundException;
import com.example.jabits_homework.error.exceptions.TokenExpiredException;
import com.example.jabits_homework.jwt.JwtProvider;
import com.example.jabits_homework.payload.request.SignInRequest;
import com.example.jabits_homework.payload.response.TokenResponse;
import com.example.jabits_homework.util.AES256;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    private final JwtProvider jwtProvider;
    private final AES256 aes256;

    @Override
    public TokenResponse signIn(SignInRequest signInRequest) {
        return userRepository.findById(signInRequest.getId())
                .filter(user -> aes256.AES_Decode(user.getPassword()).equals(signInRequest.getPassword()))
                .map(User::getUserId)
                .map(userId -> {
                    String accessToken = jwtProvider.generateAccessToken(userId);
                    String refreshToken = jwtProvider.generateRefreshToken(userId);

                    tokenRepository.save(
                            Token.builder()
                                    .refreshToken(refreshToken)
                                    .userId(userId)
                                    .build()
                    );

                    return TokenResponse.builder()
                            .accessToken(accessToken)
                            .refreshToken(refreshToken)
                            .build();
                })
                .orElseThrow(LoginFailedException::new);
    }

    @Override
    public TokenResponse refreshToken(String refreshToken) {
        if(jwtProvider.isRefreshToken(refreshToken))
            throw new RuntimeException();


        return tokenRepository.findByRefreshToken(refreshToken)
                .map(token -> {
                    String newRefreshToken = jwtProvider.generateRefreshToken(token.getUserId());

                    return token.updateRefreshToken(newRefreshToken);
                })
                .map(tokenRepository::save)
                .map(token -> {
                    String accessToken = jwtProvider.generateRefreshToken(token.getUserId());

                    return TokenResponse.builder()
                            .refreshToken(token.getRefreshToken())
                            .accessToken(accessToken)
                            .build();
                })
                .orElseThrow(RefreshTokenNotFoundException::new);
    }
}
