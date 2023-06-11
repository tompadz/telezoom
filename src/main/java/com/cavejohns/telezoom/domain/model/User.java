package com.cavejohns.telezoom.domain.model;

public class User {

    private final String shortname;
    private final String email;
    private final Long chatId;

    public User(String shortname, String email, Long chatId) {
        this.email = email;
        this.shortname = shortname;
        this.chatId = chatId;
    }

    public String getShortname() {
        return shortname;
    }

    public Long getChatId() {return chatId;}

    public String getEmail() {
        return email;
    }
}
