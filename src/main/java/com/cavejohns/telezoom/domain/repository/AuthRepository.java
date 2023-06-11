package com.cavejohns.telezoom.domain.repository;

public interface AuthRepository {

    String authUser(String email, String shortname, Long chatId);

}
