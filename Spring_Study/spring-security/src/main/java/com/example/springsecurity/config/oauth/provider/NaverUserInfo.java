package com.example.springsecurity.config.oauth.provider;

import java.util.Map;

public class NaverUserInfo implements Oauth2UserInfo {

    private Map<String, Object> attributes; // getAttributes()로 들어오는 정보를 받음

    public NaverUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getProviderId() {
        return (String) attributes.get("id");
    }

    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }
}
