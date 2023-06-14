package com.cavejohns.telezoom.data.data_base.dao;

import com.cavejohns.telezoom.data.data_base.entity.UserEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    private Connection connection;

    public UserDaoImpl() {
        try {
            String dbPath = "jdbc:sqlite:src/main/resources/data_base/sql.db";
            connection = DriverManager.getConnection(dbPath);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<UserEntity> getAll() throws SQLException, NullPointerException {
        List<UserEntity> users = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM users");

        while (resultSet.next()) {
            UserEntity user = new UserEntity();
            user.setShortname(resultSet.getString("shortname"));
            user.setChatId(resultSet.getLong("chat_id"));
            user.setEmail(resultSet.getString("email"));
            users.add(user);
        }

        return users;
    }

    @Override
    public UserEntity findByShortname(String shortname) throws NullPointerException, SQLException {
        UserEntity user = null;
        String sql = "SELECT * FROM users WHERE shortname = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, shortname);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            user = new UserEntity();
            user.setShortname(resultSet.getString("shortname"));
            user.setChatId(resultSet.getLong("chat_id"));
            user.setEmail(resultSet.getString("email"));
        }

        return user;
    }

    @Override
    public UserEntity findByChatId(Long chatId) throws SQLException {
        UserEntity user = null;
        String sql = "SELECT * FROM users WHERE chat_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setLong(1, chatId);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            user = new UserEntity();
            user.setShortname(resultSet.getString("shortname"));
            user.setChatId(resultSet.getLong("chat_id"));
            user.setEmail(resultSet.getString("email"));
        }

        return user;
    }

    @Override
    public void addUser(String shortname, Long chatId, String email) throws NullPointerException, SQLException {
        UserEntity user = findByChatId(chatId);
        if (user == null) {
            String sql = "INSERT INTO users (shortname, chat_id, email) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, shortname);
            statement.setLong(2, chatId);
            statement.setString(3, email);
            statement.executeUpdate();
        }else {
            String updateSql = "UPDATE users SET email = ? WHERE chat_id = ?";
            PreparedStatement updateStatement = connection.prepareStatement(updateSql);
            updateStatement.setString(1, email);
            updateStatement.setLong(2, chatId);
            updateStatement.executeUpdate();
        }
    }

}
