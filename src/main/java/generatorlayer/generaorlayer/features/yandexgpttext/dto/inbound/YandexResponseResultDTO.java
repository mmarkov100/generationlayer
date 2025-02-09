package generatorlayer.generaorlayer.features.yandexgpttext.dto.inbound;

/*
DTO который приходит от яндекса
 */

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Data
public class YandexResponseResultDTO {

    @JsonProperty("result")
    private Result result;

    @Setter
    @Getter
    @Data
    public static class Result {

        @JsonProperty("alternatives")
        private List<Alternative> alternatives;

        @JsonProperty("usage")
        private Usage usage;

        @JsonProperty("modelVersion")
        private String modelVersion;

    }

    @Setter
    @Getter
    @Data
    public static class Alternative {

        @JsonProperty("message")
        private Message message;

        @JsonProperty("status")
        private String status;

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

    @Setter
    @Getter
    @Data
    public static class Usage {

        @JsonProperty("inputTextTokens")
        private String inputTextTokens;

        @JsonProperty("completionTokens")
        private String completionTokens;

        @JsonProperty("totalTokens")
        private String totalTokens;

        @JsonProperty("completionTokensDetails")
        private Object completionTokensDetails; // Может быть null, поэтому Object

    }
}
