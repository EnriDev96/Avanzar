package com.Proyecto.Avanzar.Security;

public class JwtRequest {

    private String username;
    private String password;

    private String accessToken;

    public JwtRequest(){

    }

    public JwtRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getAccessToken() {
        return accessToken;
    }

}
