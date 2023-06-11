package com.cavejohns.telezoom.utils;

/**
 * Логгер для удобного вывода сообщений в консоль
 */
public class Log {

    /**
     * Отправит сообщение с информацией в консоль;
     * @param tag - тег, для поиска в консоли;
     * @param message - сообщение для вывода в консоль;
     */
    public static void i(String tag, String message) {
        System.out.println(tag + ": " + message);
    }

    /**
     * Отправит сообщение с ошибкой в консоль;
     * @param tag - тег, для поиска в консоли;
     * @param message - сообщение для вывода в консоль;
     */
    public static void e(String tag, String message) {
        System.err.println(tag + ": " + message);
    }
}
