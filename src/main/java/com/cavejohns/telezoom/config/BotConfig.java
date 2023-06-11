package com.cavejohns.telezoom.config;


public class BotConfig {

    final String token, name;

    public BotConfig(String token, String name) {
        this.token = token;
        this.name = name;
    }

    public String getName() {return name;}

    public String getToken() {
        return token;
    }

}
