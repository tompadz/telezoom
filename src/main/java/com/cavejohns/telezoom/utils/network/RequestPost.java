package com.cavejohns.telezoom.utils.network;

import com.cavejohns.telezoom.utils.Log;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

/**
 * Класс RequestPost представляет POST-запрос.
 * Он наследуется от класса Request и параметризуется типом T.
 * @param <T> - тип данных, возвращаемых в ответ на запрос
 */
public class RequestPost<T> extends Request<T> {

    private final String url;
    private final Class<T> responseType;
    private final Object body;


    /**
     * Конструктор класса RequestPost.
     * @param url - URL-адрес, по которому выполняется запрос
     * @param responseType - класс типа данных, возвращаемых в ответ на запрос
     * @param body - тело запроса
     * @param token - токен аутентификации
     */
    public RequestPost(String url, Class<T> responseType, Object body, String token) {
        super(token);
        this.url = url;
        this.responseType = responseType;
        this.body = body;
    }

    /**
     * Метод execute выполняет POST-запрос.
     * @return объект Response, содержащий результат выполнения запроса
     */
    @Override
    public Response<T> execute() {
        HttpEntity<Object> entity = new HttpEntity<>(body, headers);
        try {
            ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.POST, entity, responseType);
            return handleResponse(response);
        }catch (HttpClientErrorException e) {
            return new Response<>(e, ResponseState.ERROR);
        }
    }
}
