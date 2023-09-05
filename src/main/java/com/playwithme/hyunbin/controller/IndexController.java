package com.playwithme.hyunbin.controller;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
@Slf4j
public class IndexController {

    @GetMapping("/")
    public String index(Model model, HttpSession session){

        String nickname = (String) session.getAttribute("nickname");

        if(nickname != null) {
            log.info("로그인 유저 닉네임 {}", nickname);
            model.addAttribute("nickname", nickname);
        } else {
            log.info("전달된 유저 닉네임 값 없음.");
        }

        return "index";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, Model model) {
        session.invalidate();

        // 로그아웃 플래그 추가
        model.addAttribute("loggedOut", true);

        return "login/logoutAlert";
    }


}
