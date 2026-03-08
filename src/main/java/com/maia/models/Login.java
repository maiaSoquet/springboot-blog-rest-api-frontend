package com.maia.models;

public class Login {
    private String usernameOrEmail;
    private String password;

    public Login () {}

    public Login(String usernameOrEmail, String password) {
        this.usernameOrEmail = usernameOrEmail;
        this.password = password;
    }

    public String getUsernameOrEmail() {return usernameOrEmail;}
    public void setUsernameOrEmail(String usernameOrEmail) {this.usernameOrEmail = usernameOrEmail;}

    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}
}
