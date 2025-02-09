package generatorlayer.generaorlayer.features.yandexgpttext.dto.outbound;

/*
DTO который приходит от яндекса, где содержится ответ на запрос генерации
 */

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Data
public class ChatRequestDTO {

    @JsonProperty("modelUri")
    private String modelUri;

    @JsonProperty("completionOptions")
    private CompletionOptions completionOptions;

    @JsonProperty("messages")
    private List<Message> messages;

    @Setter
    @Getter
    @Data
    public static class CompletionOptions {

        @JsonProperty("stream")
        private boolean stream;

        @JsonProperty("maxTokens")
        private int maxTokens;

        @JsonProperty("temperature")
        private double temperature;

        @JsonProperty("reasoningOptions")
        private ReasoningOptions reasoningOptions;

        public CompletionOptions(boolean stream, int maxTokens, double temperature, ReasoningOptions reasoningOptions) {
            this.stream = stream;
            this.maxTokens = maxTokens;
            this.temperature = temperature;
            this.reasoningOptions = reasoningOptions;
        }

        @Getter
        @Setter
        @Data
        public static class ReasoningOptions {

            @JsonProperty("mode")
            private String mode;

            public ReasoningOptions(String mode) {
                this.mode = mode;
            }
        }
    }

    @Setter
    @Getter
    @Data
    public static class Message {

        @JsonProperty("role")
        private String role;

        @JsonProperty("text")
        private String text;

    }
}
