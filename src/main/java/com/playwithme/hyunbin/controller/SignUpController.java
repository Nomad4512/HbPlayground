package com.playwithme.hyunbin.controller;

import com.playwithme.hyunbin.domain.User;
import com.playwithme.hyunbin.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
@Slf4j
public class SignUpController {

    private final UserService userService;

    // 회원가입 폼 이동
    @GetMapping("/signup")
    public String signUp(Model model){
        model.addAttribute("user", new User());
        return "login/signupForm";
    }

    // 회원가입
    @PostMapping("/signup")
    public String signUp(@ModelAttribute User user, Model model){
        model.addAttribute("user", new User());

        userService.createAccount(user);
        return "redirect:/";
    }
}
