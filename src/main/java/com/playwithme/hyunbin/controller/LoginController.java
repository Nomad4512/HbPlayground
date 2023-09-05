package com.playwithme.hyunbin.controller;

import com.playwithme.hyunbin.service.oauth2.GetUserInfoService;
import com.playwithme.hyunbin.service.oauth2.RestJsonService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
@Slf4j
public class LoginController {

        private final RestJsonService restJsonService;
        private final GetUserInfoService getUserInfoService;

    @GetMapping("")
    public String loginForm(){
        return "login/loginForm";
    }

    @GetMapping("/kakao")
    public String kakao(@RequestParam(required = false) String code, Model model, HttpSession session){ // Access_token 발급

        //access_token이 포함된 JSON String을 받아온다.
        String accessTokenJsonData = restJsonService.getAccessTokenJsonData(code);
        if("error".equals(accessTokenJsonData)) return "error";

        //JSON String -> JSON Object
        JSONObject accessTokenJsonObject = new JSONObject(accessTokenJsonData);

        //access_token 추출
        String accessToken = accessTokenJsonObject.get("access_token").toString();


        //유저 정보가 포함된 JSON String을 받아온다.
        String userInfo = getUserInfoService.getUserInfo(accessToken);


        //JSON String -> JSON Object
        JSONObject userInfoJsonObject = new JSONObject(userInfo);

        //유저의 Email 추출
        JSONObject propertiesJsonObject = (JSONObject)userInfoJsonObject.get("properties");

        String nickname = propertiesJsonObject.get("nickname").toString();
        session.setAttribute("nickname", nickname);

        JSONObject kakaoAccountJsonObject = (JSONObject)userInfoJsonObject.get("kakao_account");

        String email;
        try{
            email = kakaoAccountJsonObject.get("email").toString();
        }
        catch (Exception e){
            email = "약관 동의 안함";
            log.info(nickname + "유저 email 약관 동의 안함.");
        }

        model.addAttribute("email", email);

        return "redirect:/";
    }

}
