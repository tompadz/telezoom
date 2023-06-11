package com.cavejohns.telezoom.bot.commands;

/**
 * Для локализаци лучше использовать .properties
 * Сделано для упрощения. Временный файл.
 */
public class CommandsConst {

    /** Start command **/
    public static final String START_COMMAND = "/start";
    public static final String START_NAME = "Начать";
    public static final String START_INFO = "Запускает бота";
    public static final String START_TEXT = "Спасибо что запустили бота";


    /** Login command **/
    public static final String LOGIN_COMMAND = "/login";
    public static final String LOGIN_NAME = "Авторизоваться";
    public static final String LOGIN_INFO = "Сохраняет почту в базу данных";
    public static final String LOGIN_SUCCESS = "Пользователь добавлен";
    public static final String LOGIN_ERROR = "Ошибка подключения к базе данных";

    /** Create command **/
    public static final String CREATE_COMMAND = "/create";
    public static final String CREATE_NAME = "Создать встречу";
    public static final String CREATE_INFO = "Создает встречу в zoom";
    public static final String CREATE_TEXT = "Создаю встречу...";
    public static final String CREATE_LINK_INFO = "Ссылка на встречу";
    public static final String CREATE_UNKNOWN_ERROR = "Ошибка!";
    public static final String CREATE_DATA_BASE_ERROR = "Ошибка чтения базы данных";
    public static final String CREATE_USER_ERROR = "Пользователь не обнаружен";


    /** Info command **/
    public static final String INFO_COMMAND = "/info";
    public static final String INFO_NAME = "Информация";
    public static final String INFO_INFO = "Показать информацию";
}
