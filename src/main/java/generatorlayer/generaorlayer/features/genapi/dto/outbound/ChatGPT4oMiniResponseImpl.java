package generatorlayer.generaorlayer.features.genapi.dto.outbound;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
public class ChatGPT4oMiniResponseImpl implements GenApiResponse {
    private long id;
    private String status;
    private String responseType;
    private double cost;
    private int progress;
    private List<String> result;
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
        return "assistant";
    }

    @Override
    public String getText() {
        return result.get(result.size() - 1);
    }

    @Override
    public Integer getUsageTokens() {
        return 0;
    }

    @Override
    public Integer getCompletionTokens() {
        return 0;
    }

    @Override
    public Integer getAllTokens() {
        return 0;
    }

    @Override
    public double getCost(){
        return cost;
    }

    @Getter
    @Setter
    @Data
    public static class FullResponse {
        private int index;
        private Message message;
        private Object logprobs;
        private String finishReason;

        @Getter
        @Setter
        @Data
        public static class Message {
            private String role;
            private String content;
            private Object refusal;
        }
    }

    @Getter
    @Setter
    @Data
    public static class Parameters {
        private int n;
        private double topP;
        private boolean stream;
        private boolean isSync;
        private List<MessageContent> messages;
        private int maxTokens;
        private double temperature;
        private ResponseFormat responseFormat;
        private double presencePenalty;
        private double frequencyPenalty;

        @Getter
        @Setter
        @Data
        public static class MessageContent {
            private String text;
            private String type;
        }

        @Getter
        @Setter
        @Data
        public static class ResponseFormat {
            private String type;
        }
    }
}
