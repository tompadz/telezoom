package com.cavejohns.telezoom.domain.model;

import java.util.StringJoiner;

import static com.cavejohns.telezoom.bot.commands.CommandsConst.CREATE_LINK_INFO;

public class Meeting {

    private final Long id;
    private final String joinUrl;
    private final String name;

    public Meeting(Long id, String joinUrl, String name) {
        this.id = id;
        this.joinUrl = joinUrl;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getJoinUrl() {
        return joinUrl;
    }

    public String getName() {
        return name;
    }

    public String getMessageText() {
        StringJoiner joiner = new StringJoiner("\n");
        joiner.add(CREATE_LINK_INFO + " - " + joinUrl);
        return joiner.toString();
    }
}
