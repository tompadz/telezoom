package com.cavejohns.telezoom.utils.network;

import com.cavejohns.telezoom.utils.Log;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Абстрактный класс Request представляет базовый класс для отправки запросов.
 * @param <T> Тип данных ответа на запрос.
 */
public abstract class Request<T> {

    protected final String TAG = "Request";
    protected final RestTemplate restTemplate;

    protected HttpHeaders headers;

    /**
     * Конструктор класса Request.
     * @param jwtToken Токен для аутентификации.
     */
    public Request(String jwtToken) {
        restTemplate = new RestTemplateBuilder()
                .additionalMessageConverters(new MappingJackson2HttpMessageConverter())
                .build();

        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(jwtToken);
    }

    /**
     * Добавляет заголовок к запросу.
     * @param name Имя заголовка.
     * @param value Значение заголовка.
     */
    public void addHeader(String name, String value) {
        headers.add(name, value);
    }

    /**
     * Удаляет заголовок из запроса.
     * @param name Имя заголовка.
     */
    public void removeHeader(String name) {
        headers.remove(name);
    }

    /**
     * Выполняет запрос.
     * @return Response<T> Ответ на запрос.
     */
    public abstract Response<T> execute();

    /**
     * Обрабатывает ответ от сервера.
     * @param response Ответ от сервера.
     * @return Объект Response с данными ответа.
     */
    protected Response<T> handleResponse(ResponseEntity<T> response) {
        if (response.getStatusCode().is2xxSuccessful()) {
            return handleSuccessResponse(response);
        } else {
            return handleRestErrorResponse();
        }
    }

    /**
     * Обрабатывает успешный ответ от сервера.
     * @param response Ответ от сервера.
     * @return Объект Response с данными ответа.
     */
    private Response<T> handleSuccessResponse(ResponseEntity<T> response) {
        try {
            String jsonResponse = convertResponseToJson(response);
            Log.i(TAG, jsonResponse);
            return new Response<>(response.getBody(), ResponseState.SUCCESS);
        } catch (JsonProcessingException e) {
            return handleJsonErrorResponse();
        }
    }

    /**
     * Преобразует ответ от сервера в JSON-строку.
     * @param response Ответ от сервера.
     * @return JSON-строка с данными ответа.
     * @throws JsonProcessingException Исключение при преобразовании в JSON.
     */
    private String convertResponseToJson(ResponseEntity<T> response) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(response.getBody());
    }

    /**
     * Обрабатывает ошибку запроса к серверу.
     * @return Объект Response с ошибкой.
     */
    private Response<T> handleRestErrorResponse() {
        Throwable error = new RuntimeException("Ошибка запроса сервера");
        return new Response<>(error, ResponseState.ERROR);
    }

    /**
     * Обрабатывает ошибку при преобразовании ответа в JSON.
     * @return Объект Response с ошибкой.
     */
    private Response<T> handleJsonErrorResponse() {
        Throwable error = new RuntimeException("Ошибка при преобразовании ответа в JSON");
        return new Response<>(error, ResponseState.ERROR);
    }
}