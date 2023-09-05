package com.playwithme.hyunbin.service.oauth2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("naverService")
public class NaverOAuthService extends OAuth2Service {

    public NaverOAuthService(@Value("${oauth2.naver.clientId}") String clientId,
                             @Value("${oauth2.naver.clientSecret}") String clientSecret,
                             @Value("${oauth2.naver.redirectUri}") String redirectUri) {

        super("authorization_code",
                clientId,
                redirectUri,
                clientSecret,
                "https://nid.naver.com/oauth2.0/token");
    }
}