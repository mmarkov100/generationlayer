package yandexgeneratorlayer.yandexgeneratorlayer.service;

/*
Этот сервис отвечает за выполнение HTTP-запросов к Yandex API.
Про отправку curl запроса тут https://yandex.cloud/ru/docs/foundation-models/operations/yandexgpt/create-chat

Полная логика метода sendRequest:
	1. Формирование HTTP-запроса:
		○ Объект HttpEntity объединяет тело запроса (jsonRequest) и заголовки (headers).
	2. Отправка HTTP-запроса:
		○ Используется метод exchange из RestTemplate для выполнения POST-запроса.
		○ Параметры:
			§ URL (url): Из конфигурации.
			§ Метод (HttpMethod.POST): Указывает тип запроса.
			§ Сущность (entity): Тело и заголовки.
			§ Тип ответа (String.class): Ответ в виде строки.
	3. Обработка ответа:
		○ Если статус ответа OK (200), возвращается тело ответа (response.getBody()).
В противном случае выбрасывается исключение с сообщением об ошибке.
 */

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import yandexgeneratorlayer.yandexgeneratorlayer.YandexConfig;

@Service
public class YandexCurlService {

    private final YandexConfig yandexConfig;

    private final RestTemplate restTemplate;

    public YandexCurlService(YandexConfig yandexConfig, RestTemplate restTemplate) {
        this.yandexConfig = yandexConfig;
        this.restTemplate = restTemplate;
    }

    // Формирует и отправляет HTTP-запрос
    public String sendRequest(String jsonRequest) throws Exception {
        String token = yandexConfig.getIamToken();
        String folderId = yandexConfig.getFolderId();
        String url = yandexConfig.getApiUrl();

        //	1. Формирование HTTP-запроса:
        //Объект HttpEntity объединяет тело запроса (jsonRequest) и заголовки (headers).
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);
        headers.set("x-folder-id", folderId);

        HttpEntity<String> entity = new HttpEntity<>(jsonRequest, headers);

        // 	2. Отправка HTTP-запроса:
        //		○ Используется метод exchange из RestTemplate для выполнения POST-запроса.
        //		○ Параметры:
        //			§ URL (url): Из конфигурации.
        //			§ Метод (HttpMethod.POST): Указывает тип запроса.
        //			§ Сущность (entity): Тело и заголовки.
        //Тип ответа (String.class): Ответ в виде строки.
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        //	3. Обработка ответа:
        //		○ Если статус ответа OK (200), возвращается тело ответа (response.getBody()).
        //		○ В противном случае выбрасывается исключение с сообщением об ошибке.
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new Exception("Failed to get response from Yandex API: " + response.getStatusCode());
        }
    }
}
