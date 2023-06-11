package com.cavejohns.telezoom.bot.commands.actions;

import com.cavejohns.telezoom.bot.buttons.BotButtons;
import com.cavejohns.telezoom.bot.commands.CommandsConst;
import com.cavejohns.telezoom.bot.commands_handler.UpdateData;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class StartCommandStrategy implements CommandStrategy {

    @Override
    public String getTag() {
        return "StartCommandStrategy";
    }

    @Override
    public void execute(UpdateData data) {
        SendMessage message = new SendMessage();
        message.setChatId(data.getChatId());
        message.setText(CommandsConst.START_TEXT);
        message.setReplyMarkup(BotButtons.inlineMarkup());
        tryExecute(data.getBot(), message);
    }
}
