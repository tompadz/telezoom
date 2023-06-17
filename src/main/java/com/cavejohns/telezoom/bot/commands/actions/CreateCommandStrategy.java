package com.cavejohns.telezoom.bot.commands.actions;

import com.cavejohns.telezoom.bot.TelegramZoomBot;
import com.cavejohns.telezoom.bot.commands_handler.UpdateData;
import com.cavejohns.telezoom.domain.model.CreateWebData;
import com.cavejohns.telezoom.domain.model.MeetingResult;
import com.cavejohns.telezoom.domain.model.WebDataWrapper;
import com.cavejohns.telezoom.domain.use_case.MeetingUseCase;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.cavejohns.telezoom.bot.commands.CommandsConst.CREATE_TEXT;

public class CreateCommandStrategy implements CommandStrategy {

    final MeetingUseCase useCase;

    /**
     * Конструктор класса CreateCommandStrategy.
     * @param service - сервис Zoom, используемый для создания встречи
     */
    public CreateCommandStrategy(MeetingUseCase service) {
        this.useCase = service;
    }

    @Override
    public String getTag() {
        return "CreateCommandStrategy";
    }

    /**
     * Метод execute выполняет стратегию команды "create".
     * Он отправляет сообщение о создании встречи, вызывает метод создания встречи в сервисе Zoom
     * и обновляет сообщение с результатом операции.
     */
    @Override
    public void execute(UpdateData data) {
        if (data.getWebData() != null) {
            CreateWebData createWebData = parseCreateWebData(data.getWebData());
            if (createWebData != null) {
                executeWithWebData(data, createWebData);
            }
            return;
        }
        if (data.getCommandText() != null) {
            CommandData commandData = parseCommandText(data.getCommandText());
            if (commandData != null) {
                executeWithCommandData(data, commandData);
            }
        }
    }

    /**
     * Выполеяет стратегию команды "create" используя данные из CreateWebData
     */
    private void executeWithWebData(UpdateData data, CreateWebData createWebData) {
        boolean isHaveUsers = createWebData.getUsers() != null;
        CommandData commandData = new CommandData(createWebData.getName(), createWebData.getUsers(), createWebData.getStartDate());
        if (isHaveUsers) {
            createMeetingWithUsers(commandData, data);
        }else {
            createMeeting(commandData, data);
        }
    }

    /**
     * Выполеяет стратегию команды "create" используя данные из CommandData
     */
    private void executeWithCommandData(UpdateData data, CommandData commandData) {
        boolean isHaveUsers = commandData.userIds.isEmpty();
        if (isHaveUsers) {
            createMeetingWithUsers(commandData, data);
        }else {
            createMeeting(commandData, data);
        }
    }

    /**
     * Создает стандартную встречу, доступ к которой будет у всех пользователей.
     */
    private void createMeeting(CommandData commandData, UpdateData updateData) {
        Message message = sendCreateMessage(updateData.getBot(), updateData.getChatId());
        String result = useCase.createMeeting(commandData.commandName, commandData.startDate);
        updateMessage(updateData.getBot(), updateData.getChatId(), message, result);
    }

    /**
     * Создает приватную встречу, доступ к которой будет только у пользователей их доступного списка.
     */
    private void createMeetingWithUsers(CommandData commandData, UpdateData updateData) {
        Message message = sendCreateMessage(updateData.getBot(), updateData.getChatId());
        MeetingResult result = useCase.createMeeting(commandData.commandName, commandData.startDate, commandData.userIds);
        if (result.getErrorMessage() != null) {
            updateMessage(updateData.getBot(), updateData.getChatId(), message, result.getErrorMessage());
            return;
        }
        if (result.getChatIds() != null) {
            notifyAllUsers(result.getChatIds(), updateData.getBot(), result.getMemberMessage());
        }
        updateMessage(updateData.getBot(), updateData.getChatId(), message, result.getCreateMessage());
    }

    /**
     * Уведомляет всех пользователей о созданной приватной встречи,
     * выполняется только после метода createMeetingWithUsers
     */
    private void notifyAllUsers(List<Long> ids, TelegramZoomBot bot, String message) {
        for (Long id : ids) {
            SendMessage initialMessage = new SendMessage();
            initialMessage.setChatId(id);
            initialMessage.setText(message);
            tryExecute(bot, initialMessage);
        }
    }

    /**
     * Метод sendCreateMessage отправляет сообщение о создании встречи.
     * @param bot - экземпляр TelegramZoomBot
     * @param chatId - идентификатор чата
     * @return объект Message, представляющий отправленное сообщение
     */
    private Message sendCreateMessage(TelegramZoomBot bot, Long chatId) {
        SendMessage initialMessage = new SendMessage();
        initialMessage.setChatId(chatId);
        initialMessage.setText(CREATE_TEXT);
        return tryExecute(bot, initialMessage);
    }


    /**
     * Метод updateMessage обновляет сообщение с результатом операции.
     * @param bot - экземпляр TelegramZoomBot
     * @param chatId - идентификатор чата
     * @param message - объект Message, который необходимо обновить
     * @param text - текст, который нужно установить в обновленном сообщении
     */
    private void updateMessage(TelegramZoomBot bot, Long chatId, Message message, String text) {
        EditMessageText editMessage = new EditMessageText();
        editMessage.setChatId(chatId);
        editMessage.setMessageId(message.getMessageId());
        editMessage.setText(text);
        tryExecute(bot, editMessage);
    }

    /**
     * Получает и преобразовывает текстовую команду в CommandData
     * таким образом из команды /create "name" @user @user можно
     * получить готовый CommandData для упращенной роботы
     * @param commandText тест команды
     * @return Преобразованная CommandData
     */
    private CommandData parseCommandText(String commandText) {
        Pattern pattern = Pattern.compile("/create(?:\\s+\"([^\"]*)\")?(?:\\s+(.*))?");
        Matcher matcher = pattern.matcher(commandText);

        if (matcher.matches()) {
            String commandName = matcher.group(1);
            String userIdsText = matcher.group(2);

            List<String> userIds = new ArrayList<>();
            if (userIdsText != null && !userIdsText.isEmpty()) {
                String[] userIdsArray = userIdsText.trim().replace("@", "").split("\\s+");
                userIds.addAll(Arrays.asList(userIdsArray));
            }

            return new CommandData(commandName, userIds);
        }

        System.out.println("Invalid command format");
        return null;
    }

    /**
     * Получает и преобразовывает json данные из web приложения
     * в CreateWebData, таким образом получив ответ от приложения
     * можно создать готовый CreateWebData для упращенной роботы
     * @param webData json из веб приложения
     * @return преобразованный CreateWebData
     */
    private CreateWebData parseCreateWebData(String webData) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            WebDataWrapper<CreateWebData> createDataWrapper = objectMapper.readValue(webData, new TypeReference<>() {});
            return createDataWrapper.getData();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static class CommandData {

        private final String commandName;
        private final List<String> userIds;
        private final String startDate;

        public CommandData(String commandName, List<String> userIds) {
            this.commandName = commandName;
            this.userIds = userIds;
            this.startDate = null;
        }

        public CommandData(String commandName, List<String> userIds, String startDate) {
            this.commandName = commandName;
            this.userIds = userIds;
            this.startDate = startDate;
        }

        public String getStartDate() {return startDate;}

        public String getCommandName() {
            return commandName;
        }

        public List<String> getUserIds() {
            return userIds;
        }
    }
}
