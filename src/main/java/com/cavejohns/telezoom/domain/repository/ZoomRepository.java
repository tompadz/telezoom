package com.cavejohns.telezoom.domain.repository;

import com.cavejohns.telezoom.data.rest.dto.MeetingDto;
import com.cavejohns.telezoom.utils.network.Response;

import java.util.List;

public interface ZoomRepository {

    Response<MeetingDto> createMeeting(String name, List<String> emails);

    Response<MeetingDto> createMeeting(String name);

}
