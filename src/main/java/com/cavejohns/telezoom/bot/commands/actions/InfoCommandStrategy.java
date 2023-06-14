package com.cavejohns.telezoom.bot.commands.actions;

import com.cavejohns.telezoom.bot.buttons.BotButtons;
import com.cavejohns.telezoom.bot.commands.BotCommands;
import com.cavejohns.telezoom.bot.commands_handler.UpdateData;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.webapp.SentWebAppMessage;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;

public class InfoCommandStrategy implements CommandStrategy {

    @Override
    public String getTag() {
        return "InfoCommandStrategy";
    }

    @Override
    public void execute(UpdateData data) {
        SendMessage message = new SendMessage();
        message.setChatId(data.getChatId());
        message.setText(BotCommands.getInfo());
        message.setReplyMarkup(BotButtons.inlineMarkup());
        tryExecute(data.getBot(), message);
    }
}
