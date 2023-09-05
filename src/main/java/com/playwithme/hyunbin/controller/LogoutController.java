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

    // 네이버 소셜 로그아웃 (API가 없어서 access_token삭제로 로그아웃 구현)
    @PostMapping("/naver/logout")
    public ResponseEntity<?> naverLogout(@RequestBody String accessToken) {
        String apiUrl = String.format("https://nid.naver.com/oauth2.0/token?grant_type=delete&client_id=%s&client_secret=%s&access_token=%s&service_provider=NAVER", naverClientId, naverSecretKey, accessToken);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, null, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response.getBody());
        }
    }
}
