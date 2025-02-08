package generatorlayer.generaorlayer.features.yandexgpttext.dto.inbound;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class YandexResponseResultDTO {

    @JsonProperty("result")
    private Result result;

    @Setter
    @Getter
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
    public static class Alternative {

        @JsonProperty("message")
        private Message message;

        @JsonProperty("status")
        private String status;

    }

    @Setter
    @Getter
    public static class Message {

        @JsonProperty("role")
        private String role;

        @JsonProperty("text")
        private String text;

    }

    @Setter
    @Getter
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
