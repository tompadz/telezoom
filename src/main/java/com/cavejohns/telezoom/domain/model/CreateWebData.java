package com.cavejohns.telezoom.domain.model;

import java.util.List;

public class CreateWebData {

    private String name;
    private String startDate;
    private List<String> users;

    public CreateWebData() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }
}
