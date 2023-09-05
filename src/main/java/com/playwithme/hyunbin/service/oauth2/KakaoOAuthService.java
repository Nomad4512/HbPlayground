package com.playwithme.hyunbin.service.oauth2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("kakaoService")
public class KakaoOAuthService extends OAuth2Service{
    public KakaoOAuthService(@Value("${oauth2.kakao.clientId}") String clientId,
                             @Value("${oauth2.kakao.clientSecret}") String clientSecret,
                             @Value("${oauth2.kakao.redirectUri}") String redirectUri) {

        super("authorization_code",
                clientId,
                redirectUri,
                clientSecret,
                "https://kauth.kakao.com/oauth/token");
    }
}
