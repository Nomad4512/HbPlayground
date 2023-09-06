package com.playwithme.hyunbin.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

@Controller
public class LogoutController {

    // 카카오 소셜 로그아웃
    @GetMapping("/kakao/logout")
    public String logoutKakao(HttpSession session, Model model) {
        session.invalidate();

        // 로그아웃 플래그 추가
        model.addAttribute("loggedOut", true);

        return "login/logoutAlert";
    }

    @Value("${oauth2.naver.clientId}")
    private String naverClientId;

    @Value("${oauth2.naver.clientSecret}")
    private String naverSecretKey;

    @PostMapping("/naver/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok().build();
    }
}
