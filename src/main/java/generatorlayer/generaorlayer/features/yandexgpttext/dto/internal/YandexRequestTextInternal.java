package generatorlayer.generaorlayer.features.yandexgpttext.dto.internal;

/*
Специальный дто для преобразования вместо приходящего дто
 */

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class YandexRequestTextInternal {

    @JsonProperty("modelUri")
    @NotNull(message = "Model URI must not be null")
    private String modelUri;

    @JsonProperty("completionOptions")
    @NotNull(message = "Completion options must not be null")
    private CompletionOptions completionOptions;

    @JsonProperty("messages")
    @NotEmpty(message = "Messages list must not be empty")
    private List<Message> messages;

    // Вложенные классы
    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class CompletionOptions {
        // Описывает, требуется ли потоковая обработка.
        @JsonProperty("stream")
        private boolean stream;

        @JsonProperty("maxTokens")
        @Min(value = 1, message = "Max tokens must be greater than 0")
        @Max(value = 2000, message = "Max tokens must not exceed 2000")
        private int maxTokens;

        @JsonProperty("temperature")
        @Min(value = 0, message = "Temperature must be between 0 and 1")
        @Max(value = 1, message = "Temperature must be between 0 and 1")
        private double temperature;

        @JsonProperty("reasoningOptions")
        private ReasoningOptions reasoningOptions;

        @Getter
        @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        @Data
        public static class ReasoningOptions {
            // Нужно ли нейросети "размышлять при ответе"
            @JsonProperty("mode")
            private String mode;
        }

    }

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Message {
        @JsonProperty("role")
        @NotNull(message = "Role must not be null")
        private String role;

        @JsonProperty("text")
        @NotNull(message = "Text must not be null")
        @Size(min = 1, max = 2000, message = "Text length must be between 1 and 2000 characters") //Задаем границу сообщения в 2к символов
        private String text;

    }
}
