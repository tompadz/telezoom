package com.cavejohns.telezoom.data.rest.request;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MeetingSettingsRequest {
    private Boolean join_before_host;
    public List<MeetingInvitees> meeting_invitees;

    private Boolean registrants_email_notification = true;

    private Boolean registrants_confirmation_email = true;

    public void setMeeting_invitees(List<MeetingInvitees> meeting_invitees) {
        this.meeting_invitees = meeting_invitees;
    }

    public List<MeetingInvitees> getMeeting_invitees() {
        return meeting_invitees;
    }

    public Boolean getJoin_before_host() {
        return join_before_host;
    }

    public Boolean getRegistrants_email_notification() {return registrants_email_notification;}

    public Boolean getRegistrants_confirmation_email() {return registrants_confirmation_email;}

    public void setJoin_before_host(Boolean join_before_host) {
        this.join_before_host = join_before_host;
    }
}
