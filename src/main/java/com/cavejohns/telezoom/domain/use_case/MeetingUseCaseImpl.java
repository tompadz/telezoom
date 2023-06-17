package com.cavejohns.telezoom.domain.use_case;

import com.cavejohns.telezoom.data.data_base.dao.UserDao;
import com.cavejohns.telezoom.data.data_base.entity.UserEntity;
import com.cavejohns.telezoom.data.rest.dto.MeetingDto;
import com.cavejohns.telezoom.domain.model.Meeting;
import com.cavejohns.telezoom.domain.model.MeetingResult;
import com.cavejohns.telezoom.domain.model.User;
import com.cavejohns.telezoom.domain.repository.ZoomRepository;
import com.cavejohns.telezoom.utils.network.Response;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.cavejohns.telezoom.bot.commands.CommandsConst.*;

public class MeetingUseCaseImpl implements MeetingUseCase {

    private final ZoomRepository zoomRepository;
    private final UserDao userDao;

    public MeetingUseCaseImpl(ZoomRepository zoomRepository, UserDao userDao) {
        this.zoomRepository = zoomRepository;
        this.userDao = userDao;
    }

    @Override
    public String createMeeting(String name, String startDate) {
        Response<MeetingDto> result = zoomRepository.createMeeting(name, startDate);
        switch (result.getState()) {
            case SUCCESS -> {
                MeetingDto dto = result.getResult();
                Meeting meeting = dto.toMeeting();
                return meeting.getMessageText();
            }
            case ERROR -> {
                return result.getError().getMessage();
            }
        }
        return CREATE_UNKNOWN_ERROR;
    }

    @Override
    public MeetingResult createMeeting(String name, String startDate, List<String> shortnames) {
        try {
            List<User> users = findUsers(shortnames);
            if (users == null) return showError(CREATE_USER_ERROR);
            List<String> emails = users.stream().map(User::getEmail).toList();
            List<Long> ids = users.stream().map(User::getChatId).toList();
            Response<MeetingDto> result = zoomRepository.createMeeting(name, startDate, emails);

            switch (result.getState()) {
                case SUCCESS -> {
                    MeetingDto dto = result.getResult();
                    Meeting meeting = dto.toMeeting();
                    return new MeetingResult(meeting, ids);
                }
                case ERROR -> {
                    return showError(result.getError().getMessage());
                }
            }

            return showError(CREATE_UNKNOWN_ERROR);
        } catch (SQLException e) {
            e.printStackTrace();
            return showError(CREATE_DATA_BASE_ERROR);
        } catch (Throwable t) {
            t.printStackTrace();
            return showError(CREATE_UNKNOWN_ERROR);
        }
    }

    private MeetingResult showError(String message) {
        return new MeetingResult(null,null,message);
    }

    private List<User> findUsers(List<String> shortnames) throws SQLException {
        List<User> users = new ArrayList<>();
        for (String shortname : shortnames) {
            UserEntity userDto = userDao.findByShortname(shortname);
            if (userDto == null) return null;
            users.add(userDto.toUser());
        }
        return users;
    }

}
