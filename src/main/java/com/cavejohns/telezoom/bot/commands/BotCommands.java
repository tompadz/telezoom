package com.cavejohns.telezoom.bot.commands;



import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import static com.cavejohns.telezoom.bot.commands.CommandsConst.*;

public interface BotCommands {

    CustomBotCommand START = new CustomBotCommand(START_COMMAND, START_NAME, START_INFO);
    CustomBotCommand CREATE = new CustomBotCommand(CREATE_COMMAND, CREATE_NAME, CREATE_INFO);
    CustomBotCommand INFO = new CustomBotCommand(INFO_COMMAND, INFO_NAME, INFO_INFO);
    CustomBotCommand LOGIN = new CustomBotCommand(LOGIN_COMMAND, LOGIN_NAME, LOGIN_INFO);

    List<CustomBotCommand> COMMANDS = List.of(START, CREATE, INFO, LOGIN);

    /**
     * Возвращает список команд в формате Telegram.
     * @return список команд в формате Telegram
     */
    static List<BotCommand> getTelegramCommands() {
        return COMMANDS
                .stream()
                .map(CustomBotCommand::toTelegramButton)
                .collect(Collectors.toList());
    }

    /**
     * Возвращает объект команды по указанной команде.
     * @param command - команда
     * @return объект CustomBotCommand или null, если команда не найдена
     */
    static CustomBotCommand getCommand(String command) {
        return COMMANDS.stream()
                .filter(c -> command.equals(c.getCommand()))
                .findFirst()
                .orElse(null);
    }

    /**
     * Возвращает информацию о доступных командах.
     * @return информация о командах
     */
    static String getInfo() {
        StringJoiner stringJoiner = new StringJoiner("\n");
        BotCommands.COMMANDS.forEach(command -> {
            String name = command.getName();
            String info = command.getInfoText();
            stringJoiner.add(name + " - " + info);
        });
        return stringJoiner.toString();
    }

    /**
     * Проверяет, является ли указанная команда допустимой командой.
     * @param command - команда
     * @return true, если команда допустима, иначе false
     */
    static boolean isCommand(String command) {
        return COMMANDS.stream().anyMatch(c -> command.equals(c.getCommand()));
    }
}
