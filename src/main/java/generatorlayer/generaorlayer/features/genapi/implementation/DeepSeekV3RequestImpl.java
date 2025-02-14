package generatorlayer.generaorlayer.features.genapi.implementation;

import generatorlayer.generaorlayer.features.genapi.model.GenApiRequest;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
public class DeepSeekV3RequestImpl implements GenApiRequest {

    private List<Message> messages;
    private double temperature;

    // Геттеры и сеттеры

    @Getter
    @Setter
    @Data
    public static class Message {
        private String role;
        private String content;

        // Геттеры и сеттеры
    }
}
