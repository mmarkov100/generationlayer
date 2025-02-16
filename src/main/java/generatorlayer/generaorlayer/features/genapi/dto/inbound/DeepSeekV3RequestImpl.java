package generatorlayer.generaorlayer.features.genapi.dto.inbound;

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

    @Getter
    @Setter
    @Data
    public static class Message {
        private String role;
        private String content;
    }
}
