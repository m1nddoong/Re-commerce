package com.example.market.domain.auth.dto.oauth2;

import java.util.Map;

public class NaverResponse implements OAuth2Response {
    // 데이터를 받은 attribute 변수
    private final Map<String, Object> attribute;

    // 네이버는 response 내부에 key 가 담기기 떄문에 response 를 get 한 뒤 attribute 에 넣어주기
    public NaverResponse(Map<String, Object> attribute) {

        this.attribute = (Map<String, Object>) attribute.get("response");
    }

    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getProviderId() {
        return attribute.get("id").toString();
    }

    @Override
    public String getEmail() {
        return attribute.get("email").toString();
    }

    @Override
    public String getName() {
        return attribute.get("name").toString();
    }

    @Override
    public String getProfileUrl() { return attribute.get("profile_image").toString(); }
}
