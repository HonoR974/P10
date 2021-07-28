package com.clientui.model;

public class UserBean {

    private  int id;
    private String username;

    public UserBean()
    {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "id = " + id +
                ", username = '" + username + '\'' +
                '}';
    }
}
