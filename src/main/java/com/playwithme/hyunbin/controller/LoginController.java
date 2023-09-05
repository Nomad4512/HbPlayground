package com.playwithme.hyunbin.controller;

import com.playwithme.hyunbin.service.oauth2.GetUserInfoService;
import com.playwithme.hyunbin.service.oauth2.OAuth2Service;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/login")
@Slf4j
public class LoginController {

    private final OAuth2Service kakaoService;
    private final OAuth2Service naverService;
    private final GetUserInfoService getUserInfoService;

    public LoginController(
            @Qualifier("kakaoService") OAuth2Service kakaoService,
            @Qualifier("naverService") OAuth2Service naverService,
            GetUserInfoService getUserInfoService) {
        this.kakaoService = kakaoService;
        this.naverService = naverService;
        this.getUserInfoService = getUserInfoService;
    }

    @GetMapping("")
    public String loginForm(){
        return "login/loginForm";
    }

    @GetMapping("/kakao")
    public String kakaoLogin(@RequestParam(required = false) String code, Model model, HttpSession session){ // Access_token 발급
        return handleAccessToken(code, kakaoService,"KAKAO", model, session);


    }

    @GetMapping("/naver")
    public String naverLogin(@RequestParam(required = false) String code, Model model, HttpSession session){ // Access_token 발급
        return handleAccessToken(code, naverService,"NAVER", model, session);
    }


    // 겹치는 부분 메소드화 및 중간에 분기점 나누기
    private String handleAccessToken(String code, OAuth2Service oAuth2Service, String platform, Model model, HttpSession session) {
        // Access_token 발급
        String accessTokenJsonData = oAuth2Service.getAccessTokenJsonData(code);
        if("error".equals(accessTokenJsonData)) return "error";

        //JSON String -> JSON Object
        JSONObject accessTokenJsonObject = new JSONObject(accessTokenJsonData);

        // 에러체크
        if (accessTokenJsonObject.has("error")) {
            String error = accessTokenJsonObject.getString("error");
            String errorDescription = accessTokenJsonObject.optString("error_description", "No description provided"); // error_description이 없는 경우 기본 메시지 제공

            log.error("OAuth Error: " + error);
            log.error("Error Description: " + errorDescription);

            return "redirect:/";
        }

        //access_token 추출
        String accessToken = accessTokenJsonObject.get("access_token").toString();


        //유저 정보가 포함된 JSON String을 받아온다.
        String userInfo = getUserInfoService.getUserInfo(accessToken, platform);


        //JSON String -> JSON Object
        JSONObject userInfoJsonObject = new JSONObject(userInfo);

        String nickname = null;
        String email = null;

        // 카카오 로그인시 유저정보 획득
        if ("KAKAO".equals(platform)) {
            JSONObject propertiesJsonObject = userInfoJsonObject.getJSONObject("properties");
            nickname = propertiesJsonObject.getString("nickname");

            JSONObject kakaoAccountJsonObject = userInfoJsonObject.getJSONObject("kakao_account");
            try {
                email = kakaoAccountJsonObject.getString("email");
            } catch (Exception e) {
                email = "약관 동의 안함";
                log.info(nickname + " 유저 email 약관 동의 안함.");
            }
        }
        // 네이버 로그인시 유저정보 획득
        if ("NAVER".equals(platform)) {
            JSONObject responseJsonObject = userInfoJsonObject.getJSONObject("response");
            nickname = responseJsonObject.getString("nickname");
            try {
                email = responseJsonObject.getString("email");
            } catch (Exception e) {
                email = "약관 동의 안함";
                log.info(nickname + " 유저 email 약관 동의 안함.");
            }
        }
        // 그 외에
        if (!"KAKAO".equals(platform) && !"NAVER".equals(platform)){
            // 다른 플랫폼 지원 시 처리 로직 추가
            throw new IllegalArgumentException("지원되지 않는 플랫: " + platform);
        }

        session.setAttribute("nickname", nickname);
        session.setAttribute("platform", platform);
        model.addAttribute("email", email);

        return "redirect:/";
    }

}
