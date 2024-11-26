package yandexgeneratorlayer.yandexgeneratorlayer.service;

/*
Этот метод реализует основную логику обработки запроса к Yandex API:
	1. Создание запроса:
		○ Использует RequestBuilderService для генерации JSON-запроса.
		○ Входные данные (chatDTO) и конфигурация (yandexConfig.getFolderId()) передаются в этот сервис.
	2. Отправка запроса:
		○ Запрос отправляется в API через YandexCurlService.
		○ Возвращается ответ в формате JSON.
	3. Парсинг ответа:
		○ Ответ обрабатывается через JSONResponseParserService для извлечения текста.
		○ Возвращаемое значение формируется в виде карты:
{
    "role": "assistant",
    "text": "160, 90, 170, 80, 180.",
    "usage": {
        "completionTokens": 23,
        "inputTextTokens": 52,
        "totalTokens": 75
    }
}
 */

import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import yandexgeneratorlayer.yandexgeneratorlayer.YandexConfig;
import yandexgeneratorlayer.yandexgeneratorlayer.dto.inbound.FromAPITextGenerator.YaChatDTO;
import org.slf4j.LoggerFactory;
import yandexgeneratorlayer.yandexgeneratorlayer.dto.outbound.ToAPITextGenerator.MessageResponseBackDTO;

import java.util.Map;
import java.util.TreeMap;

@Service
public class YandexProcessingService {

    private static final Logger logger = LoggerFactory.getLogger(YandexProcessingService.class);

    private final YandexConfig yandexConfig;
    private final RequestBuilderService requestBuilderService;
    private final YandexCurlService yandexCurlService;

    public YandexProcessingService(
            YandexConfig yandexConfig,
            RequestBuilderService requestBuilderService,
            YandexCurlService yandexCurlService) {
        this.yandexConfig = yandexConfig;
        this.requestBuilderService = requestBuilderService;
        this.yandexCurlService = yandexCurlService;
    }

    public Map<String, Object> processChat(YaChatDTO chatDTO) throws Exception {
        // Создаем запрос
        String jsonRequest = requestBuilderService.buildRequest(chatDTO, yandexConfig.getFolderId());
        logger.info("Request to Yandex: {}", jsonRequest);

        // Отправляем запрос в Yandex API
        String jsonResponse = yandexCurlService.sendRequest(jsonRequest);
        logger.info("Response from Yandex: {}", jsonResponse);

        // Обрабатываем ответ через JSONResponseParserService
        MessageResponseBackDTO response = JSONResponseParserService.getResponseJSON(jsonResponse);

        // Формируем ответ
        return getStringObjectMap(response);
    }

    // Метод для создания ответа клиенту
    private static Map<String, Object> getStringObjectMap(MessageResponseBackDTO response) {
        // Формируем usage
        Map<String, Object> responseUsage = new TreeMap<>();
        responseUsage.put("inputTextTokens", response.getUsage().getInputTextTokens());
        responseUsage.put("completionTokens", response.getUsage().getCompletionTokens());
        responseUsage.put("totalTokens", response.getUsage().getTotalTokens());

        // Формируем ответ для клиента
        Map<String, Object> jsonResponseMap = new TreeMap<>();
        jsonResponseMap.put("role", response.getRole());
        jsonResponseMap.put("text", response.getText());
        jsonResponseMap.put("usage", responseUsage);
        return jsonResponseMap;
    }
}

