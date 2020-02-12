package de.lamp.cryptopanel.model;

import java.io.Serializable;

public class SigninPayload implements Serializable {
    private int token;
    private User user;

    public SigninPayload(int token, User user) {
        this.token = token;
        this.user = user;
    }

    public int getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }
}
