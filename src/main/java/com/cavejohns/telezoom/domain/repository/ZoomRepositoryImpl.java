package com.cavejohns.telezoom.domain.repository;


import com.cavejohns.telezoom.data.rest.api.ZoomApi;
import com.cavejohns.telezoom.config.JwtConfig;
import com.cavejohns.telezoom.data.rest.request.CreateMeetingRequest;
import com.cavejohns.telezoom.data.rest.request.MeetingInvitees;
import com.cavejohns.telezoom.data.rest.dto.MeetingDto;
import com.cavejohns.telezoom.data.rest.request.MeetingSettingsRequest;
import com.cavejohns.telezoom.utils.network.Response;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ZoomRepositoryImpl implements ZoomRepository, ZoomApi {

    private final JwtConfig config;

    /**
     * Конструктор класса ZoomServiceImpl.
     * @param config - JWT-конфиг с токеном для аутентификации в Zoom API
     */
    public ZoomRepositoryImpl(JwtConfig config) {
        this.config = config;
    }

    @Override
    public Response<MeetingDto> createMeeting(String name, String startDate, List<String> emails) {
        CreateMeetingRequest requestBody = new CreateMeetingRequest();
        MeetingSettingsRequest settingsRequest = new MeetingSettingsRequest();
        List<MeetingInvitees> meetingInvitees = new ArrayList<>();
        for (String email : emails) {
            MeetingInvitees invite = new MeetingInvitees();
            invite.setEmail(email);
            meetingInvitees.add(invite);
        }
        settingsRequest.setJoin_before_host(true);
        settingsRequest.setMeeting_invitees(meetingInvitees);
        requestBody.setSettings(settingsRequest);
        requestBody.setTopic(name);
        requestBody.setStart_time(startDate);
        return createZoomMeeting(requestBody).execute();
    }

    @Override
    public Response<MeetingDto> createMeeting(String name, String startDate) {
        CreateMeetingRequest requestBody = new CreateMeetingRequest();
        MeetingSettingsRequest settingsRequest = new MeetingSettingsRequest();
        settingsRequest.setJoin_before_host(true);
        requestBody.setSettings(settingsRequest);
        requestBody.setTopic(name);
        requestBody.setStart_time(startDate);
        return createZoomMeeting(requestBody).execute();
    }

    @Override
    public String getToken() { return config.generateJwtToken(); }
}
