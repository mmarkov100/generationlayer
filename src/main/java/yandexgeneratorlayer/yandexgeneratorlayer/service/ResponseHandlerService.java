package yandexgeneratorlayer.yandexgeneratorlayer.service;

/*
Этот сервис отвечает за обработку ответа от Yandex API.
Логика метода parseResponse:
	1. Парсинг JSON:
		○ Использует JSONResponseParserService для преобразования строки JSON в объект MessageResponseDTO.
	2. Извлечение текста:
После преобразования берёт текст из поля text объекта MessageResponseDTO.
 */

import org.springframework.stereotype.Service;
import yandexgeneratorlayer.yandexgeneratorlayer.dto.inbound.FromYandexServer.MessageResponseDTO;

@Service
public class ResponseHandlerService {

    public String parseResponse(String jsonResponse) throws Exception {
        MessageResponseDTO responseMessage = JSONResponseParserService.getResponseJSON(jsonResponse);
        return responseMessage.getText();
    }
}
