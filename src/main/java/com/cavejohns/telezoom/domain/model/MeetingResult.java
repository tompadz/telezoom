package com.cavejohns.telezoom.domain.model;

import java.util.List;
import java.util.StringJoiner;

public class MeetingResult {

    private final List<Long> chatIds;
    private final Meeting meeting;

    private String errorMessage;

    public MeetingResult(Meeting meeting, List<Long> chatIds, String errorMessage) {
        this.chatIds = chatIds;
        this.meeting = meeting;
        this.errorMessage = errorMessage;
    }

    public MeetingResult(Meeting meeting, List<Long> chatIds) {
        this.chatIds = chatIds;
        this.meeting = meeting;
    }

    public String getCreateMessage() {
        return meeting.getMessageText();
    }

    public String getErrorMessage() {return  errorMessage;}

    public String getMemberMessage() {
        StringJoiner joiner = new StringJoiner("\n");
        joiner.add("Организация \"Solit Clouds\" приглашает вас на конференцию Zoom!");
        joiner.add("\nНазвание встречи: " + meeting.getName());
        joiner.add(meeting.getMessageText());
        return joiner.toString();
    }

    public List<Long> getChatIds() {
        return chatIds;
    }
}
