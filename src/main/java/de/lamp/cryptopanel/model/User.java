package de.lamp.cryptopanel.model;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users_api")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String email;
    private String password;

    public User() {
    }

    public User(String name, String email, String password) {
    }

    public static String secret = "sdfjadsjfdsjds";

    public User(int id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return Hashing.sha256().hashString( this.getId() + User.secret, Charsets.UTF_8 ).toString();
    }

}

