package generatorlayer.generaorlayer.features.genapi.implementation;

import generatorlayer.generaorlayer.features.genapi.model.GenApiRequest;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
public class ChatGPT4oMiniRequestImpl implements GenApiRequest {

    private List<Message> messages;
    private double temperature;

    @Getter
    @Setter
    @Data
    public static class Message {
        private String role;
        private List<Content> content;

        // Геттеры и сеттеры

        @Getter
        @Setter
        @Data
        public static class Content {
            private String type;
            private String text;

            // Геттеры и сеттеры
        }
    }
}
