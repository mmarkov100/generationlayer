package generatorlayer.generaorlayer.features.genapi.implementation;

import generatorlayer.generaorlayer.features.genapi.model.GenApiResponse;
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
