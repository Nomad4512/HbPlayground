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

    private enum OAuth2Platform {
        KAKAO("https://kapi.kakao.com/v2/user/me"),
        NAVER("https://openapi.naver.com/v1/nid/me");

        private final String url;

        OAuth2Platform(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }
    }

    public String getUserInfo(String accessToken, String platform) {
        OAuth2Platform oAuth2Platform;
        try {
            oAuth2Platform = OAuth2Platform.valueOf(platform.toUpperCase());
        } catch (IllegalArgumentException e) {
            log.error("지원하지 않는 플랫폼: {}", platform);
            return "login/errorAlert";
        }

        try {
            StringBuilder jsonData = new StringBuilder();

            // URI를 URL객체로 저장
            URL url = new URL(oAuth2Platform.getUrl() + "?access_token=" + accessToken);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // 네이버의 경우, 다른 헤더가 필요하면 여기에 추가.
            if (oAuth2Platform == OAuth2Platform.NAVER) {
                connection.setRequestProperty("Authorization", "Bearer " + accessToken);
            }

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
