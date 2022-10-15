package com.example.springsecurity.config.oauth.provider;

// 각 Provider별(구글, 페이스북, 네이버) 키 값이 달라서 생기는 하드 코딩을 방지하기 위한 인터페이스
public interface Oauth2UserInfo {
    String getProviderId();
    String getProvider();
    String getEmail();
    String getName();
}
