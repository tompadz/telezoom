package com.cavejohns.telezoom.bot.commands_handler;

import com.cavejohns.telezoom.bot.TelegramZoomBot;
import com.cavejohns.telezoom.bot.commands.BotCommands;
import com.cavejohns.telezoom.bot.commands.actions.*;
import com.cavejohns.telezoom.bot.commands.CustomBotCommand;
import com.cavejohns.telezoom.domain.repository.AuthRepository;
import com.cavejohns.telezoom.domain.use_case.MeetingUseCase;
import com.cavejohns.telezoom.utils.Log;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.HashMap;
import java.util.Map;

public class CommandsHandler implements BotHandler {

    private static final String TAG = "CommandsHandler";

    final MeetingUseCase meetingUseCase;
    final AuthRepository repository;

    private Map<CustomBotCommand, CommandStrategy> commandActions;

    /**
     * Конструктор класса CommandsHandler.
     * @param service - сервис Zoom, используемый для выполнения команд
     */
    public CommandsHandler(MeetingUseCase service, AuthRepository repository) {
        this.meetingUseCase = service;
        this.repository = repository;
        createActions();
    }

    /**
     * Метод createActions создает карту команд и соответствующих стратегий.
     */
    private void createActions() {
        commandActions = new HashMap<>();
        commandActions.put(BotCommands.START, new StartCommandStrategy());
        commandActions.put(BotCommands.CREATE, new CreateCommandStrategy(meetingUseCase));
        commandActions.put(BotCommands.INFO, new InfoCommandStrategy());
        commandActions.put(BotCommands.LOGIN, new LoginCommandStrategy(repository));
    }

    /**
     * Метод handleUpdates обрабатывает обновления бота.
     * @param bot - экземпляр TelegramZoomBot
     * @param update - объект Update, содержащий информацию об обновлении
     */
    @Override
    public void handleUpdates(TelegramZoomBot bot, Update update) {
        if (update.hasCallbackQuery()) {
            handleButtonUpdate(bot, update);
        }
        if (update.hasMessage()) {
            boolean isWebMessage = update.getMessage().getWebAppData() != null;
            if (isWebMessage) {
                handleWebUpdate(bot, update);
            }else {
                handleMessageUpdate(bot, update);
            }
        }
    }

    /**
     * Метод handleWebUpdate обрабатывает обновление из веб приложения.
     * @param bot - экземпляр TelegramZoomBot
     * @param update - объект Update, содержащий информацию об обновлении
     */
    private void handleWebUpdate(TelegramZoomBot bot, Update update) {
        Long chatId = update.getMessage().getChatId();
        User user = update.getMessage().getFrom();
        String webData = update.getMessage().getWebAppData().getData();
        Log.i("TEST_WEB_REQUEST", webData);
        UpdateData data = new UpdateData(update, chatId, user, bot, null, webData);
        handleCommand(data);
    }

    /**
     * Метод handleButtonUpdate обрабатывает обновление кнопки.
     * @param bot - экземпляр TelegramZoomBot
     * @param update - объект Update, содержащий информацию об обновлении
     */
    private void handleButtonUpdate(TelegramZoomBot bot, Update update) {
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        String buttonCallback = update.getCallbackQuery().getData();
        User user = update.getCallbackQuery().getFrom();
        UpdateData data = new UpdateData(update, chatId, user, bot, buttonCallback);
        handleCommand(data);
    }

    /**
     * Метод handleMessageUpdate обрабатывает обновление сообщения.
     * @param bot - экземпляр TelegramZoomBot
     * @param update - объект Update, содержащий информацию об обновлении
     */
    private void handleMessageUpdate(TelegramZoomBot bot, Update update) {
        Long chatId = update.getMessage().getChatId();
        String commandText = update.getMessage().getText();
        User user = update.getMessage().getFrom();
        UpdateData data = new UpdateData(update, chatId, user, bot, commandText);
        handleCommand(data);
    }

    /**
     * Метод handleCommand обрабатывает команду.
     */
    private void handleCommand(UpdateData data) {
        if (data.getWebData() != null) {
            handleCommandWeb(data);
        }else {
            handleCommandText(data);
        }
    }

    /**
     * Метод handleCommandText обрабатывает команды из веб приложения.
     */
    private void handleCommandWeb(UpdateData data) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(data.getWebData());
            String type = jsonNode.path("type").asText();
            CommandStrategy commandAction = findStrategy(type);
            if (commandAction != null) {
                commandAction.execute(data);
            }else {
                logUnknownError(data.getCommandText());
            }
        }catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    /**
     * Метод handleCommandText обрабатывает текстовые команды.
     */
    private void handleCommandText(UpdateData data) {
        CommandStrategy commandAction = findStrategy(data.getCommandText());
        if (commandAction != null) {
            commandAction.execute(data);
        }else {
            logUnknownError(data.getCommandText());
        }
    }

    private CommandStrategy findStrategy(String commandText) {
        return commandActions.entrySet()
                .stream()
                .filter(entry -> commandText.contains(entry.getKey().getCommand()))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(null);
    }

    /**
     * Метод logUnknownError записывает сообщение об ошибке.
     * @param text - текст ошибки
     */
    private void logUnknownError(String text) {
        Log.e(TAG, "Unknown message - " + text);
    }

}
