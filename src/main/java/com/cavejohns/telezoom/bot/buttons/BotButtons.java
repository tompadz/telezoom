package com.cavejohns.telezoom.bot.buttons;

import com.cavejohns.telezoom.bot.commands.BotCommands;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

public class BotButtons implements BotCommands {

    public static InlineKeyboardMarkup inlineMarkup() {
        InlineKeyboardButton createButton = new InlineKeyboardButton(CREATE.getName());
        createButton.setCallbackData(CREATE.getCommand());

        InlineKeyboardButton infoButton = new InlineKeyboardButton(INFO.getName());
        infoButton.setCallbackData(INFO.getCommand());

        InlineKeyboardButton loginButton = new InlineKeyboardButton(LOGIN.getName());
        loginButton.setCallbackData(LOGIN.getCommand());

        List<InlineKeyboardButton> rowInline = List.of(createButton, infoButton);
        List<InlineKeyboardButton> authLine = List.of(loginButton);
        List<List<InlineKeyboardButton>> rowsInLine = List.of(rowInline, authLine);

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        markupInline.setKeyboard(rowsInLine);

        return markupInline;
    }
}
