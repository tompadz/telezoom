package com.cavejohns.telezoom.utils.network;

import org.springframework.http.*;

/**
 * Класс RequestGet представляет GET-запрос.
 * Он наследуется от класса Request и параметризуется типом T.
 * @param <T> - тип данных, возвращаемых в ответ на запрос
 */
public class RequestGet<T> extends Request<T> {

    private final String url;
    private final Class<T> responseType;

    /**
     * Конструктор класса RequestGet.
     * @param url - URL-адрес, по которому выполняется запрос
     * @param responseType - класс типа данных, возвращаемых в ответ на запрос
     * @param token - токен аутентификации
     */
    public RequestGet(String url, Class<T> responseType, String token) {
        super(token);
        this.url = url;
        this.responseType = responseType;
    }

    /**
     * Метод execute выполняет GET-запрос.
     * @return объект Response, содержащий результат выполнения запроса
     */
    @Override
    public Response<T> execute() {
        HttpEntity<Object> entity = new HttpEntity<>(null, headers);
        ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.GET, entity, responseType);
        return handleResponse(response);
    }
}
