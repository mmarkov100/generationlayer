package yandexgeneratorlayer.yandexgeneratorlayer.service;

/*
Этот сервис отвечает за преобразование входных данных (YaChatDTO) в формат JSON для отправки в Yandex API.
Логика метода buildRequest:
	1. Создание внутреннего DTO:
		○ Используется ChatInternalDTO, чтобы собрать данные для запроса.
		○ Поля modelUri и completionOptions напрямую устанавливаются из входного DTO (YaChatDTO).
	2. Обработка сообщений:
		○ Список сообщений из YaChatDTO преобразуется в объекты MessageInternalDTO.
		○ Они добавляются к ChatInternalDTO с помощью метода addMessage.
	3. Формирование JSON:
		○ В конце данные упаковываются в ChatToJSON, вероятно, через преобразование в JSON-строку (этот шаг обрывается в просмотре).
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import yandexgeneratorlayer.yandexgeneratorlayer.dto.inbound.FromAPITextGenerator.YaChatDTO;
import yandexgeneratorlayer.yandexgeneratorlayer.dto.inbound.FromAPITextGenerator.YaMessageDTO;
import yandexgeneratorlayer.yandexgeneratorlayer.dto.internal.ChatInternalDTO;
import yandexgeneratorlayer.yandexgeneratorlayer.dto.internal.MessageInternalDTO;
import yandexgeneratorlayer.yandexgeneratorlayer.dto.outbound.ToYandexServer.ChatToJSON;

import java.util.Iterator;
import java.util.List;

@Service
public class RequestBuilderService {

    private static final Logger logger = LoggerFactory.getLogger(RequestBuilderService.class);

    // Метод удаления для генерации первых сообщений, если количество токенов превышает грань
    public String buildRequest(YaChatDTO chatDTO, String folderId, int maxTokens) {
        int totalTokens = countTokens(chatDTO.getMessages());

        // Удаляем старые сообщения, пока токены превышают лимит
        while (totalTokens > maxTokens && chatDTO.getMessages().size() > 2) {
            logger.info("Total tokens: {}. Removing oldest message pair.", totalTokens);

            // Удаляем первое пользовательское сообщение и его ответ
            Iterator<YaMessageDTO> iterator = chatDTO.getMessages().iterator();

            while (iterator.hasNext()) {
                YaMessageDTO message = iterator.next();
                if ("user".equalsIgnoreCase(message.getRole())) {
                    iterator.remove(); // Удаляем сообщение пользователя
                    if (iterator.hasNext()) {
                        iterator.next();
                        iterator.remove(); // Удаляем сообщение нейросети
                    }
                    break;
                }
            }

            // Пересчитываем токены
            totalTokens = countTokens(chatDTO.getMessages());
        }

        // Если все еще превышает лимит — бросаем исключение
        if (totalTokens > maxTokens) {
            throw new IllegalArgumentException("Input exceeds token limit of " + maxTokens);
        }

        // Создаем DTO для отправки в Yandex API
        ChatInternalDTO chatInternal = new ChatInternalDTO();
        chatInternal.setModelUri(chatDTO.getModelUri());
        chatInternal.setCompletionOptions(chatDTO.getCompletionOptions());

        chatDTO.getMessages().forEach(
                message -> chatInternal.addMessage(new MessageInternalDTO(message.getRole(), message.getText()))
        );

        ChatToJSON chatToJSON = new ChatToJSON(chatInternal);
        chatToJSON.setModelUri("gpt://" + folderId + "/" + chatToJSON.getModelUri());

        return chatToJSON.toJSON();
    }

    // Примерный подсчет токенов по количеству слов
    public static int countTokens(List<YaMessageDTO> messages) {
        int tokens = 0;
        for (YaMessageDTO message : messages) {
            tokens += message.getText().split("\\s+").length; // Разделение по пробелам
        }
        logger.info("Tokens counted: {}", tokens*2.3);
        return (int) (tokens*2.3);
    }
}
