package yandexgeneratorlayer.yandexgeneratorlayer.dto.inbound.FromAPITextGenerator;

/*
Класс YaChatDTO чётко описывает структуру входящих данных
 */

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class YaChatDTO {

    // Идентификатор модели, которая будет использоваться для генерации ответа
    @JsonProperty("modelUri")
    @NotNull(message = "Model URI must not be null")
    private String modelUri;

    // Параметры конфигурации запроса
    @JsonProperty("completionOptions")
    @NotNull(message = "Completion options must not be null")
    private YaCompletionOptionsDTO completionOptions;

    // Список сообщений, которые задают контекст для модели
    @JsonProperty("messages")
    @NotEmpty(message = "Messages list must not be empty")
    private List<YaMessageDTO> messages = new ArrayList<>();
}
