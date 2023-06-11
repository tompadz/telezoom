package com.cavejohns.telezoom.bot.commands_handler;

import com.cavejohns.telezoom.bot.TelegramZoomBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

public class UpdateData {

    private final Update update;
    private final Long chatId;
    private final User user;
    private final TelegramZoomBot bot;
    private final String commandText;

    public UpdateData(Update update, Long charId, User user, TelegramZoomBot bot, String commandText) {
        this.update = update;
        this.chatId = charId;
        this.user = user;
        this.bot = bot;
        this.commandText = commandText;
    }

    public Update getUpdate() {
        return update;
    }

    public Long getChatId() {
        return chatId;
    }

    public User getUser() {
        return user;
    }

    public TelegramZoomBot getBot() {
        return bot;
    }

    public String getCommandText() {
        return commandText;
    }
}
