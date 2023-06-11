package com.cavejohns.telezoom.domain.repository;

import com.cavejohns.telezoom.data.data_base.dao.UserDao;

import java.sql.SQLException;

import static com.cavejohns.telezoom.bot.commands.CommandsConst.LOGIN_ERROR;
import static com.cavejohns.telezoom.bot.commands.CommandsConst.LOGIN_SUCCESS;

public class AuthRepositoryImpl implements AuthRepository {

    private final UserDao dao;

    public AuthRepositoryImpl(UserDao dao) {
        this.dao = dao;
    }

    @Override
    public String authUser(String email, String shortname, Long chatId) {
        try {
            dao.addUser(shortname, chatId, email);
            return LOGIN_SUCCESS;
        } catch (SQLException e) {
            e.printStackTrace();
            return LOGIN_ERROR;
        }
    }
}
