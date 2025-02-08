package generatorlayer.generaorlayer.features.yandexgpttext.dto.outbound;

/*
ДТО который отсылаем обратно клиенту уже со сгенерированным ответом
 */

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class YandexResponseTextGenerate {

    @JsonProperty("message")
    private MessageDTO message;

    @JsonProperty("usage")
    private UsageDTO usage;

    @Setter
    @Getter
    public static class MessageDTO {

        @JsonProperty("role")
        private String role;

        @JsonProperty("text")
        private String text;

    }

    @Setter
    @Getter
    public static class UsageDTO {
        @JsonProperty("totalTokens")
        private int totalTokens;

        @JsonProperty("completionTokens")
        private int completionTokens;

        @JsonProperty("inputTextTokens")
        private int inputTextTokens;

    }
}
