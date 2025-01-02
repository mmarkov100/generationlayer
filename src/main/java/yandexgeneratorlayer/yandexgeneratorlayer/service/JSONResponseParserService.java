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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import yandexgeneratorlayer.yandexgeneratorlayer.dto.inbound.FromYandexServer.MessageResponseDTO;
import yandexgeneratorlayer.yandexgeneratorlayer.dto.inbound.FromYandexServer.ResponseDTO;
import yandexgeneratorlayer.yandexgeneratorlayer.dto.inbound.FromYandexServer.UsageDTO;
import yandexgeneratorlayer.yandexgeneratorlayer.dto.outbound.ToAPITextGenerator.MessageResponseBackDTO;
import yandexgeneratorlayer.yandexgeneratorlayer.dto.outbound.ToAPITextGenerator.ResponseBackDTO;
import yandexgeneratorlayer.yandexgeneratorlayer.dto.outbound.ToAPITextGenerator.UsageResponseBackDTO;

@Service
public class JSONResponseParserService {

    private static final Logger logger = LoggerFactory.getLogger(JSONResponseParserService.class);

    public static ResponseBackDTO getResponseJSON(String jsonResponse){

        logger.debug("Starting JSON parsing for response: {}", jsonResponse);

        // Десериализация JSON в объект Response
        Gson gson = new Gson();
        ResponseDTO responseFromYandexAPI = gson.fromJson(jsonResponse, ResponseDTO.class);

        logger.debug("Parsed ResponseDTO: {}", responseFromYandexAPI);

        // Получаем сообщение
        MessageResponseDTO messageResponseDTO = responseFromYandexAPI.getResult().getAlternatives()[0].getMessage();

        // Получаем количество затраченных токенов
        UsageDTO usageDTO = responseFromYandexAPI.getResult().getUsage();

        // Собираем количество затраченных токенов
        UsageResponseBackDTO usageResponseBackDTO = new UsageResponseBackDTO();
        usageResponseBackDTO.setInputTextTokens(usageDTO.getInputTextTokens());
        usageResponseBackDTO.setCompletionTokens(usageDTO.getCompletionTokens());
        usageResponseBackDTO.setTotalTokens(usageDTO.getTotalTokens());

        // Собираем сообщение
        MessageResponseBackDTO messageResponseBackDTO = new MessageResponseBackDTO();
        messageResponseBackDTO.setText(messageResponseDTO.getText());
        messageResponseBackDTO.setRole(messageResponseDTO.getRole());

        // Собираем ответ
        ResponseBackDTO responseBackDTO = new ResponseBackDTO();
        responseBackDTO.setMessage(messageResponseBackDTO);
        responseBackDTO.setUsage(usageResponseBackDTO);

        // Извлечение сообщения
        return responseBackDTO;
    }
}
