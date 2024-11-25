package yandexgeneratorlayer.yandexgeneratorlayer.service;

/*
Этот сервис выполняет парсинг JSON-ответа от Yandex API и извлекает ключевые данные.
Логика метода getResponseJSON:
	1. Десериализация JSON:
		○ Используется библиотека Gson для преобразования строки JSON в объект ResponseDTO.
	2. Извлечение данных:
		○ Из объекта ResponseDTO извлекается первый альтернативный результат (Alternatives), а затем его сообщение (Message).
 */

import com.google.gson.Gson;
import org.springframework.stereotype.Service;
import yandexgeneratorlayer.yandexgeneratorlayer.dto.inbound.FromYandexServer.MessageResponseDTO;
import yandexgeneratorlayer.yandexgeneratorlayer.dto.inbound.FromYandexServer.ResponseDTO;

@Service
public class JSONResponseParserService {
    public static MessageResponseDTO getResponseJSON(String jsonResponse){

        // Десериализация JSON в объект Response
        Gson gson = new Gson();
        ResponseDTO response = gson.fromJson(jsonResponse, ResponseDTO.class);

        // Извлечение сообщения
        return response.getResult().getAlternatives()[0].getMessage();
    }
}
