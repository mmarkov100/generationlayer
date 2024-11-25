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
		○ Ответ обрабатывается через ResponseHandlerService для извлечения текста.
		○ Возвращаемое значение формируется в виде карты:
{"role":"assistant","text":"ответ от Yandex API"}
 */

import org.springframework.stereotype.Service;
import yandexgeneratorlayer.yandexgeneratorlayer.YandexConfig;
import yandexgeneratorlayer.yandexgeneratorlayer.dto.inbound.FromAPITextGenerator.YaChatDTO;

import java.util.HashMap;
import java.util.Map;

@Service
public class YandexProcessingService {

    private final YandexConfig yandexConfig;
    private final RequestBuilderService requestBuilderService;
    private final YandexCurlService yandexCurlService;
    private final ResponseHandlerService responseHandlerService;

    public YandexProcessingService(
            YandexConfig yandexConfig,
            RequestBuilderService requestBuilderService,
            YandexCurlService yandexCurlService,
            ResponseHandlerService responseHandlerService) {
        this.yandexConfig = yandexConfig;
        this.requestBuilderService = requestBuilderService;
        this.yandexCurlService = yandexCurlService;
        this.responseHandlerService = responseHandlerService;
    }

    public Map<String, String> processChat(YaChatDTO chatDTO) throws Exception {
        // Создаем запрос
        String jsonRequest = requestBuilderService.buildRequest(chatDTO, yandexConfig.getFolderId());

        // Отправляем запрос в Yandex API
        String jsonResponse = yandexCurlService.sendRequest(jsonRequest);

        // Обрабатываем ответ
        String responseText = responseHandlerService.parseResponse(jsonResponse);
        Map<String, String> jsonResponseMap = new HashMap<>();
        jsonResponseMap.put("role", "assistant");
        jsonResponseMap.put("text", responseText);
        return jsonResponseMap;
    }
}
