package com.cavejohns.telezoom.bot.commands.actions;

import com.cavejohns.telezoom.bot.TelegramZoomBot;
import com.cavejohns.telezoom.bot.commands_handler.UpdateData;
import com.cavejohns.telezoom.utils.Log;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;

/**
 * Интерфейс CommandStrategy определяет стратегию выполнения команды.
 */
public interface CommandStrategy {

    /**
     * Получает тег команды.
     * @return тег команды
     */
    String getTag();

    /**
     * Выполняет команду.
     */
    void execute(UpdateData data);

    /**
     * Попытаться выполнить метод TelegramZoomBot и вернуть результат.
     * @param <T> - тип возвращаемого результата
     * @param <Method> - тип метода BotApiMethod
     * @param bot - экземпляр TelegramZoomBot для выполнения метода
     * @param method - объект метода BotApiMethod для выполнения
     * @return результат выполнения метода или null, если возникла ошибка
     */
    default <T extends Serializable, Method extends BotApiMethod<T>> T tryExecute(TelegramZoomBot bot, Method method) {
        try {
            return bot.execute(method);
        } catch (TelegramApiException e) {
            Log.e(getTag(), e.getMessage());
            return null;
        }
    }
}
