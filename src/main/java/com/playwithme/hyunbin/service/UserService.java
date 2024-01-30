package com.playwithme.hyunbin.service;

import com.playwithme.hyunbin.common.DateTimeFormatterService;
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
    private  final DateTimeFormatterService dateTimeFormatterService;

    // 회원가입
    public User createAccount(User user) {

        LocalDateTime now = LocalDateTime.now();
        String formattedTime = dateTimeFormatterService.formatLocalDateTime(now);
        user.setSignupDate(formattedTime);

        this.userRepository.save(user);

        return user;
    }


}
