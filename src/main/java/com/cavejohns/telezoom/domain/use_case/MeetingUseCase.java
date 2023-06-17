package com.cavejohns.telezoom.domain.use_case;


import com.cavejohns.telezoom.domain.model.MeetingResult;

import java.util.List;

public interface MeetingUseCase {

    public MeetingResult createMeeting(String name, String startDate, List<String> meetingInvites);

    public String createMeeting(String name, String startDate);

}
