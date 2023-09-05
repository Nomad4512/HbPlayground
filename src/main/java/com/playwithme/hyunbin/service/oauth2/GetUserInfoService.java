package com.playwithme.hyunbin.service.oauth2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
@Slf4j
public class GetUserInfoService {
    private final String HTTP_REQUEST = "https://kapi.kakao.com/v2/user/me";

    public String getUserInfo(String accessToken){
        try {
            StringBuilder jsonData = new StringBuilder();

            // URI를 URL객체로 저장
            URL url = new URL(HTTP_REQUEST + "?access_token=" + accessToken);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // 응답 데이터 읽기
            BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line;
            while((line = bf.readLine()) != null){
                jsonData.append(line);
            }
            log.info("전달된 유저 정보: {}", jsonData);
            return jsonData.toString();

        } catch(Exception e) {
            log.error("에러발생 유저 정보: ", e);
            return "login/errorAlert";
        }
    }
}
