package com.cavejohns.telezoom.bot.commands;

import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

public class CustomBotCommand {

    private final String command, name, infoText;

    public CustomBotCommand(String command, String name, String infoText) {
        this.command = command;
        this.name = name;
        this.infoText = infoText;
    }

    public BotCommand toTelegramButton() {
        return new BotCommand(command, name);
    }

    public String getCommand() {
        return command;
    }

    public String getName() {
        return name;
    }

    public String getInfoText() {
        return infoText;
    }
}
