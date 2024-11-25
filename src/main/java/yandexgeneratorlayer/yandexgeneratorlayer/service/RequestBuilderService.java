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

import org.springframework.stereotype.Service;
import yandexgeneratorlayer.yandexgeneratorlayer.dto.inbound.FromAPITextGenerator.YaChatDTO;
import yandexgeneratorlayer.yandexgeneratorlayer.dto.internal.ChatInternalDTO;
import yandexgeneratorlayer.yandexgeneratorlayer.dto.internal.MessageInternalDTO;
import yandexgeneratorlayer.yandexgeneratorlayer.dto.outbound.ToYandexServer.ChatToJSON;

@Service
public class RequestBuilderService {

    public String buildRequest(YaChatDTO chatDTO, String folderId) {
        ChatInternalDTO chatInternal = new ChatInternalDTO();

        // Установка параметров CompletionOptions
        chatInternal.setModelUri(chatDTO.getModelUri());
        chatInternal.setCompletionOptions(chatDTO.getCompletionOptions());

        chatDTO.getMessages().forEach(
                message -> chatInternal.addMessage(new MessageInternalDTO(message.getRole(), message.getText()))
        );

        ChatToJSON chatToJSON = new ChatToJSON(chatInternal);
        chatToJSON.setModelUri("gpt://" + folderId + "/" + chatToJSON.getModelUri());

        return chatToJSON.toJSON();
    }
}
