package generatorlayer.generaorlayer.features.genapi.dto.outbound;

import com.fasterxml.jackson.annotation.JsonProperty;
import generatorlayer.generaorlayer.features.yandexgpttext.dto.outbound.YandexResponseTextGenerate;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class ResponseDTO {
    @JsonProperty("message")
    private YandexResponseTextGenerate.MessageDTO message;

    @JsonProperty("usage")
    private YandexResponseTextGenerate.UsageDTO usage;

    @Setter
    @Getter
    @Data
    public static class MessageDTO {

        @JsonProperty("role")
        private String role;

        @JsonProperty("text")
        private String text;

    }

    @Setter
    @Getter
    @Data
    public static class UsageDTO {
        @JsonProperty("totalTokens")
        private int totalTokens;

        @JsonProperty("completionTokens")
        private int completionTokens;

        @JsonProperty("inputTextTokens")
        private int inputTextTokens;

        @JsonProperty("cost")
        private double cost;

    }
}
