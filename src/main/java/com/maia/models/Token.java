package com.maia.models;

public class Token {

    private static String token;


    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        Token.token = token;
    }
}
