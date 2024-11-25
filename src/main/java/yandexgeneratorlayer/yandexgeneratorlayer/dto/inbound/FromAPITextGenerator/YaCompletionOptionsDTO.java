package yandexgeneratorlayer.yandexgeneratorlayer.dto.inbound.FromAPITextGenerator;

/*
Класс YaCompletionOptionsDTO описывает параметры генерации текста
 */

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class YaCompletionOptionsDTO {

    // Описывает, требуется ли потоковая обработка.
    @JsonProperty("stream")
    private boolean stream;

    // Устанавливает ограничение на выход модели в токенах
    @JsonProperty("maxTokens")
    @Min(value = 1, message = "Max tokens must be greater than 0")
    @Max(value = 4000, message = "Max tokens must not exceed 4000")
    private int maxTokens;

    // Чем выше значение этого параметра, тем более креативными и случайными будут ответы модели
    @JsonProperty("temperature")
    @Min(value = 0, message = "Temperature must be between 0 and 1")
    @Max(value = 1, message = "Temperature must be between 0 and 1")
    private double temperature;
}
