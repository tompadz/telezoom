package com.cavejohns.telezoom.data.data_base.dao;

import com.cavejohns.telezoom.data.data_base.entity.UserEntity;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    public List<UserEntity> getAll() throws SQLException;
    public UserEntity findByShortname(String shortname) throws SQLException;
    public UserEntity findByChatId(Long chatId) throws SQLException;
    public void addUser(String shortname, Long chatId, String email) throws SQLException;
}
