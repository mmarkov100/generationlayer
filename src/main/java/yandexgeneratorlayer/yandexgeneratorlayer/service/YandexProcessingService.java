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
 */

import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import yandexgeneratorlayer.yandexgeneratorlayer.config.YandexConfig;
import yandexgeneratorlayer.yandexgeneratorlayer.dto.inbound.FromAPITextGenerator.YaChatDTO;
import org.slf4j.LoggerFactory;
import yandexgeneratorlayer.yandexgeneratorlayer.dto.outbound.ToAPITextGenerator.ResponseBackDTO;

import java.util.Map;
import java.util.Objects;

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

        logger.info("Starting processChat for modelUri: {}", chatDTO.getModelUri());

        try{
            int maxTokens;

            switch (chatDTO.getModelUri()){
                case "yandexgpt-32k/latest":
                    maxTokens = 32000;
                    break;
                default:
                    maxTokens = 8000;
                    break;
            }

            // В запросе не может быть пустой контекст, поэтому добавляем проверку на пустоту и добавляем в контекст обычный пробел
            if (Objects.equals(chatDTO.getMessages().getFirst().getText(), "")) {
                chatDTO.getMessages().getFirst().setText(" ");
            }

            // Создаем запрос
            String jsonRequest = requestBuilderService.buildRequest(chatDTO, yandexConfig.getFolderId(), maxTokens);
            logger.info("Generated JSON request: {}", jsonRequest);

            // Отправляем запрос в Yandex API
            logger.info("Sending request to Yandex API...");
            String jsonResponse = yandexCurlService.sendRequest(jsonRequest);

            // Логируем успешный ответ
            logger.info("Received response from Yandex API");
            logger.info("Yandex API response: {}", jsonResponse);

            // Обрабатываем ответ
            ResponseBackDTO response = JSONResponseParserService.getResponseJSON(jsonResponse);

            // Формируем ответ
            logger.info("Successfully parsed response");
            return getStringObjectMap(response);
        }
        catch (Exception e){
            logger.error("Error occurred while processing chat: {}", e.getMessage(), e);
            throw e;
        }
    }



    // Метод для создания ответа клиенту
    private static Map<String, Object> getStringObjectMap(ResponseBackDTO response) {
        // Формируем usage
        Map<String, Object> responseUsage = Map.of(
                "inputTextTokens", response.getUsage().getInputTextTokens(),
                "completionTokens", response.getUsage().getCompletionTokens(),
                "totalTokens", response.getUsage().getTotalTokens()
        );

        // Формируем сообщение
        Map<String, Object> responseMessage = Map.of(
                "role", response.getMessage().getRole(),
                "text", response.getMessage().getText()
        );

        // Формируем ответ для клиента
        return Map.of(
                "message",responseMessage,
                "usage", responseUsage
        );
    }
}

