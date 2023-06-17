package com.cavejohns.telezoom.domain.model;

public class WebDataWrapper<T> {

    private String type;
    private T data;

    public WebDataWrapper() {

    }

    public void setType(String type) {
        this.type = type;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public T getData() {
        return data;
    }
}
