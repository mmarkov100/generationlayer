package generatorlayer.generaorlayer.features.genapi.dto.outbound;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
public class DeepSeekV3ResponseImpl implements GenApiResponse {

    private long id;
    private String status;
    private String responseType;
    private double cost;
    private int progress;
    private List<String> result;
    @JsonProperty("full_response")
    private List<FullResponse> fullResponse;
    private Parameters parameters;

    public String getStatus() {
        return status;
    }

    public List<String> getResult() {
        return result;
    }

    @Override
    public String getRole() {
        return "assistant";  // Возвращаем роль, если она определена
    }

    @Override
    public String getText() {
        return result != null && !result.isEmpty() ? result.get(result.size() - 1) : "";  // Безопасное извлечение текста
    }

    @Override
    public Integer getUsageTokens() {
        return (fullResponse != null && !fullResponse.isEmpty()) ? fullResponse.get(0).getUsage().getPromptTokens() : 0;
    }

    @Override
    public Integer getCompletionTokens() {
        return (fullResponse != null && !fullResponse.isEmpty()) ? fullResponse.get(0).getUsage().getCompletionTokens() : 0;
    }

    @Override
    public Integer getAllTokens() {
        return (fullResponse != null && !fullResponse.isEmpty()) ? fullResponse.get(0).getUsage().getTotalTokens() : 0;
    }

    @Override
    public double getCost() {
        return cost;
    }

    // Вложенные классы, соответствующие структуре JSON
    @Getter
    @Setter
    @Data
    public static class FullResponse {
        private String id;
        private String model;
        private String object;
        private long created;
        private List<Choice> choices;
        private Usage usage;

        @Getter
        @Setter
        @Data
        public static class Choice {
            private int index;
            private Message message;
            private String finishReason;
        }

        @Getter
        @Setter
        @Data
        public static class Message {
            private String role;
            private String content;
        }

        public Usage getUsage(){
            return usage;
        }

        @Getter
        @Setter
        @Data
        public static class Usage {
            @JsonProperty("prompt_tokens")
            private int promptTokens;
            @JsonProperty("completion_tokens")
            private int completionTokens;
            @JsonProperty("total_tokens")
            private int totalTokens;

            public int getPromptTokens() {
                return promptTokens;
            }

            public int getCompletionTokens() {
                return completionTokens;
            }

            public int getTotalTokens() {
                return totalTokens;
            }
        }
    }

    @Getter
    @Setter
    @Data
    public static class Parameters {
        private String model;
        private double topP;
        private boolean isSync;
        private List<MessageContent> messages;
        private int maxTokens;
        private double temperature;

        @Getter
        @Setter
        @Data
        public static class MessageContent {
            private String role;
            private String content;
        }
    }
}