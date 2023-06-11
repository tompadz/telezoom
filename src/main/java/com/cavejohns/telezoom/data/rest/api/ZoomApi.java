package com.cavejohns.telezoom.data.rest.api;

import com.cavejohns.telezoom.data.rest.dto.MeetingDto;
import com.cavejohns.telezoom.utils.network.RequestGet;
import com.cavejohns.telezoom.utils.network.RequestPost;

public interface ZoomApi {

    String getToken();

    default RequestGet<MeetingDto> getZoomMeetings() {
        return new RequestGet<>(
                "https://api.zoom.us/v2/users/me/meetings",
                MeetingDto.class,
                getToken()
        );
    }

    default RequestPost<MeetingDto> createZoomMeeting(Object body) {
        return new RequestPost<>(
                "https://api.zoom.us/v2/users/me/meetings",
                MeetingDto.class,
                body,
                getToken()
        );
    }
}