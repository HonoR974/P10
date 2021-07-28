package com.clientui.model;

public class TesterUser {

    private String username;
    private String jwt;
    private boolean connected;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }


    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    @Override
    public String toString() {
        return "TesterUser{" +
                "username='" + username + '\'' +
                ", jwt='" + jwt + '\'' +
                ", connected=" + connected +
                '}';
    }
}
