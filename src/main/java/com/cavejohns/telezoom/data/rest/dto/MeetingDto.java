package com.cavejohns.telezoom.data.rest.dto;


import com.cavejohns.telezoom.domain.model.Meeting;

public class MeetingDto {

    private Long id;
    private String registration_url;
    private String created_at;
    private String join_url;
    private String topic;

    private String start_time;

    public String getStart_time() {return start_time;}

    public String getTopic() {
        return topic;
    }

    public Long getId() {
        return id;
    }

    public String getRegistration_url() {
        return registration_url;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getJoin_url() {
        return join_url;
    }

    public Meeting toMeeting() {
        return new Meeting(id, join_url, topic, start_time);
    }
}