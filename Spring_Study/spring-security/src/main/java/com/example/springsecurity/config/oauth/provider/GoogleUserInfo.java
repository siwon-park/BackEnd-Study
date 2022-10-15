package com.example.springsecurity.config.oauth.provider;

import java.util.Map;

public class GoogleUserInfo implements Oauth2UserInfo {

    private Map<String, Object> attributes; // getAttributes()로 들어오는 정보를 받음

    public GoogleUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getProviderId() {
        return (String) attributes.get("sub");
    }

    @Override
    public String getProvider() {
        return "google";
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
