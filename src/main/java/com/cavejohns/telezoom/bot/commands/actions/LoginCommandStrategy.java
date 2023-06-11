package com.cavejohns.telezoom.bot.commands.actions;

import com.cavejohns.telezoom.bot.TelegramZoomBot;
import com.cavejohns.telezoom.bot.commands_handler.UpdateData;
import com.cavejohns.telezoom.domain.repository.AuthRepository;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class LoginCommandStrategy implements CommandStrategy {

    private final AuthRepository repository;

    public LoginCommandStrategy(AuthRepository repository) {
        this.repository = repository;
    }

    @Override
    public String getTag() {
        return "LoginCommandStrategy";
    }

    @Override
    public void execute(UpdateData data) {
        String shortname = data.getUser().getUserName();
        String email = getEmailFromCommandText(data.getCommandText());
        if (email == null) {
            sendEmailErrorMessage(data.getBot(), data.getChatId());
            return;
        }
        String result = repository.authUser(email, shortname, data.getChatId());
        sendResultMessage(data.getBot(), data.getChatId(), result);
    }

    private void sendEmailErrorMessage(TelegramZoomBot bot, Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Вы не ввели почту, формат сообщения /login example@email.com");
        tryExecute(bot, message);
    }

    private void sendResultMessage(TelegramZoomBot bot, Long chatId, String result) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(result);
        tryExecute(bot, message);
    }

    private String getEmailFromCommandText(String commandText) {
        String[] parts = commandText.split(" ");
        if (parts.length >= 2) {
            return parts[1];
        }
        return null;
    }
}
