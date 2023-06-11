package com.cavejohns.telezoom.bot.commands_handler;

import com.cavejohns.telezoom.bot.TelegramZoomBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface BotHandler {
    void handleUpdates(TelegramZoomBot bot, Update update);
}
