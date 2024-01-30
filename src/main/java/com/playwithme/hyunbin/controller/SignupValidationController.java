package com.playwithme.hyunbin.controller;

import com.playwithme.hyunbin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class SignupValidationController {

    private final UserRepository userRepository;

    // 이메일 중복 검사
    @GetMapping("/emailDupCheck")
    public Map<String, Boolean> emailDuplicateCheck(@RequestParam String userEmail) {
        boolean isDuplicate = emailExistsInDatabase(userEmail);
        return Collections.singletonMap("duplicate",isDuplicate);
    }

    private boolean emailExistsInDatabase(String email) {
        return userRepository.existsByUserEmail(email);
    }

    // 닉네임 중복 검사
    @GetMapping("/nameDupCheck")
    public Map<String, Boolean> nameDuplicateCheck(@RequestParam String userName) {
        boolean isDuplicate = nameExistsInDatabase(userName);
        return Collections.singletonMap("duplicate",isDuplicate);
    }

    private boolean nameExistsInDatabase(String name) {
        return userRepository.existsByUserName(name);
    }

}
