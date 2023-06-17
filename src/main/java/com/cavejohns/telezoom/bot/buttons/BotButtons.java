package com.cavejohns.telezoom.bot.buttons;

import com.cavejohns.telezoom.bot.commands.BotCommands;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;

import static com.cavejohns.telezoom.bot.commands.CommandsConst.CREATE_URL;

import java.util.ArrayList;
import java.util.List;

public class BotButtons implements BotCommands {

    public static ReplyKeyboardMarkup inlineMarkup() {

        KeyboardButton createButton = new KeyboardButton(CREATE.getName());
        WebAppInfo webAppInfo = WebAppInfo.builder().url(CREATE_URL).build();
        createButton.setWebApp(webAppInfo);

        KeyboardButton infoButton = new KeyboardButton(INFO.getCommand());
        KeyboardButton loginButton = new KeyboardButton(LOGIN.getCommand());

        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        row.add(createButton);
        keyboard.add(row);

        row = new KeyboardRow();
        row.add(infoButton);
        row.add(loginButton);
        keyboard.add(row);

        markup.setKeyboard(keyboard);
        return markup;
    }
}
