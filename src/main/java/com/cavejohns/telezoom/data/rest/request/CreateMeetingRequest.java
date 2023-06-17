package com.cavejohns.telezoom.data.rest.request;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateMeetingRequest {

    private MeetingSettingsRequest settings;

    private String topic;

    private String start_time;

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic){
        this.topic = topic;
    }

    public MeetingSettingsRequest getSettings() {
        return settings;
    }

    public void setSettings(MeetingSettingsRequest settings) {
        this.settings = settings;
    }
}
