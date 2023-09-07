package com.playwithme.hyunbin.controller;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Document;

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

        String bjTierMd = "[![Solved.ac 프로필](http://mazassumnida.wtf/api/mini/generate_badge?boj=zizonbini)](https://solved.ac/zizonbini)";
        String bjTierHtml = convertMarkdownToHtml(bjTierMd); // 백준티어 md to html 변환
        model.addAttribute("bjTier", bjTierHtml);

        return "index";
    }

    public String convertMarkdownToHtml(String markdown) {
        Parser parser = Parser.builder().build();
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        Document document = parser.parse(markdown);
        return renderer.render(document);
    }




}
