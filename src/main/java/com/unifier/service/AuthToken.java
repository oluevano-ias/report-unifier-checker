package com.unifier.service;

import org.joda.time.DateTime;

public class AuthToken {

    private String token;
    private DateTime expires;

    public AuthToken(String token, DateTime expires) {
        this.token = token;
        this.expires = expires;
    }

    public String getToken() {
        return token;
    }

    public DateTime getExpires() {
        return expires;
    }

}
