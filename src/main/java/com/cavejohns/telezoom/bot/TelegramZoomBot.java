package com.cavejohns.telezoom.bot;

import com.cavejohns.telezoom.bot.commands.BotCommands;
import com.cavejohns.telezoom.bot.commands_handler.BotHandler;
import com.cavejohns.telezoom.config.BotConfig;
import com.cavejohns.telezoom.utils.Log;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

public class TelegramZoomBot extends TelegramLongPollingBot {

    private final String TAG = "TelegramZoomBot";

    private final BotConfig config;
    private final BotHandler handler;

    /**
     * Конструктор класса TelegramZoomBot.
     * @param config - конфигурация бота
     * @param handler - обработчик бота
     */
    public TelegramZoomBot(BotConfig config, BotHandler handler) {
        super(new DefaultBotOptions(), config.getToken());
        this.config = config;
        this.handler = handler;
        executeBotCommands();
    }

    /**
     * Метод executeBotCommands выполняет команды бота.
     * Он устанавливает команды и их область видимости для бота.
     * В случае возникновения ошибки TelegramApiException, логируется сообщение об ошибке.
     */
    private void executeBotCommands() {
        try {
            List<BotCommand> commandsArray = BotCommands.getTelegramCommands();
            BotCommandScopeDefault scope = new BotCommandScopeDefault();
            SetMyCommands commands = new SetMyCommands(commandsArray, scope, null);
            execute(commands);
        } catch (TelegramApiException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    /**
     * Метод onUpdateReceived вызывается при получении обновления от Telegram.
     * Он передает полученное обновление обработчику бота для дальнейшей обработки.
     * @param update - полученное обновление от Telegram
     */
    @Override
    public void onUpdateReceived(Update update) {
        handler.handleUpdates(this, update);
    }

    /**
     * Метод getBotUsername возвращает имя бота.
     * @return имя бота
     */
    @Override
    public String getBotUsername() {
        return config.getName();
    }

}
