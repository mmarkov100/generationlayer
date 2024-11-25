package yandexgeneratorlayer.yandexgeneratorlayer.dto.internal;

/*
Этот класс представляет внутреннюю структуру данных для работы с чатом в приложении.
 */

import lombok.Getter;
import lombok.Setter;
import yandexgeneratorlayer.yandexgeneratorlayer.dto.inbound.FromAPITextGenerator.YaCompletionOptionsDTO;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ChatInternalDTO {
    private String modelUri; // Указывает модель, используемую для генерации текста.
    private CompletionInternalDTO completionOptions; // Представляет параметры генерации текста.
    private List<MessageInternalDTO> messages = new ArrayList<>(); // Список сообщений во внутреннем формате.
    private String folderId; // Уникальный идентификатор папки для работы с Yandex API. Должна быть информация о нем в applications.properties

    public ChatInternalDTO() {}

    // Добавляет сообщение в список messages
    public void addMessage(MessageInternalDTO message) {
        this.messages.add(message);
    }

    // Преобразует объект YaCompletionOptionsDTO в CompletionInternalDTO и устанавливает его как параметры генерации
    public void setCompletionOptions(YaCompletionOptionsDTO optionsDTO) {
        this.completionOptions = new CompletionInternalDTO(
                optionsDTO.isStream(),
                optionsDTO.getMaxTokens(),
                optionsDTO.getTemperature()
        );
    }
}

