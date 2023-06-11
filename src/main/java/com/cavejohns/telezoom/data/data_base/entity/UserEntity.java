package com.cavejohns.telezoom.data.data_base.entity;

import com.cavejohns.telezoom.domain.model.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class UserEntity {

    private Long chatId;
    private String shortname;
    private String email;
    private Long id;

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User toUser() {
        return new User(shortname, email, chatId);
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }
}
