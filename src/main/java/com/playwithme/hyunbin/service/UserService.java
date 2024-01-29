package com.playwithme.hyunbin.service;

import com.playwithme.hyunbin.domain.User;
import com.playwithme.hyunbin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    // 회원가입
        public User createAccount(User user) {

            user.setSignupDate(LocalDateTime.now().withNano(0));

            this.userRepository.save(user);

            return user;
        }
}
