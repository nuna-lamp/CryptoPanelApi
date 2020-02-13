package de.lamp.cryptopanel.model;

import java.io.Serializable;

public class SigninPayload implements Serializable {
    private String token;
    private User user;

    public SigninPayload(String token, User user) {
        this.token = token;
        this.user = user;
    }

    public SigninPayload() {
    }

    public String getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }
}
