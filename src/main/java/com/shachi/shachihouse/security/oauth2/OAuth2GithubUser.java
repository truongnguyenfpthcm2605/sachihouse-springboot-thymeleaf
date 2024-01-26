package com.shachi.shachihouse.security.oauth2;

import com.shachi.shachihouse.security.oauth2.OAuth2Userdetails;

import java.util.Map;

public class OAuth2GithubUser extends OAuth2Userdetails {
    public OAuth2GithubUser(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getName() {
        return  (String) attributes.get("name");
    }

    @Override
    public String getEmail() {
        return  (String) attributes.get("email");
    }
}
