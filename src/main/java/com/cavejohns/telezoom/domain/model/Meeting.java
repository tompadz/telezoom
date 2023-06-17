package com.cavejohns.telezoom.domain.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.StringJoiner;

import static com.cavejohns.telezoom.bot.commands.CommandsConst.CREATE_LINK_INFO;

public class Meeting {

    private final Long id;
    private final String joinUrl;
    private final String name;

    private final String startTime;

    public Meeting(Long id, String joinUrl, String name, String startTime) {
        this.id = id;
        this.joinUrl = joinUrl;
        this.name = name;
        this.startTime = startTime;
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

    public String getDateTimeMessage() {
        LocalDateTime dateTime = LocalDateTime.parse(startTime, DateTimeFormatter.ISO_ZONED_DATE_TIME);
        return dateTime.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.getDefault()) +
                ". " +
                dateTime.getDayOfMonth() +
                " " +
                dateTime.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()) +
                " " +
                dateTime.getYear() +
                "г в " +
                dateTime.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public String getMessageText() {
        StringJoiner joiner = new StringJoiner("\n");
        joiner.add(CREATE_LINK_INFO + " - " + joinUrl);
        return joiner.toString();
    }


}
