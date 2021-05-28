package com.example.jabits_homework.service.user;

import com.example.jabits_homework.entity.user.User;
import com.example.jabits_homework.entity.user.enums.Authority;
import com.example.jabits_homework.entity.user.repository.UserRepository;
import com.example.jabits_homework.error.exceptions.UserAlreadySignedException;
import com.example.jabits_homework.payload.request.SignUpRequest;
import com.example.jabits_homework.util.AES256;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final AES256 aes256;

    @SneakyThrows
    @Override
    public void signUp(SignUpRequest signUpRequest) {
        User user = userRepository.findById(signUpRequest.getId())
                .orElse(null);

        if(user != null)
            throw new UserAlreadySignedException();

        userRepository.save(
                User.builder()
                        .id(signUpRequest.getId())
                        .password(aes256.AES_Encode(signUpRequest.getPassword()))
                        .authority(Authority.USER)
                        .build()
        );
    }

    //기능 테스트를 위해 만든 어드민 계정 생성
    @SneakyThrows
    @Override
    public void makeAdmin() {
        userRepository.save(
                User.builder()
                        .authority(Authority.ADMIN)
                        .id("2")
                        .password(aes256.AES_Encode("2"))
                        .build()
        );
    }
}
