package com.shachi.shachihouse.security.oauth2;

import java.util.Map;

public abstract class OAuth2Userdetails {

    protected Map<String,Object> attributes;

    public OAuth2Userdetails(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public abstract String getName();
    public abstract String getEmail();

}
