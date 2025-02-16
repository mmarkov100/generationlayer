package generatorlayer.generaorlayer.features.yandexgpttext.dto.outbound;

/*
ДТО который отсылаем обратно клиенту уже со сгенерированным ответом
 */

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class YandexResponseTextGenerate {

    @JsonProperty("message")
    private MessageDTO message;

    @JsonProperty("usage")
    private UsageDTO usage;

    public void setMessage(MessageDTO message){
        this.message = message;
    }

    public void setUsage(UsageDTO usage){
        this.usage = usage;
    }

    @Setter
    @Getter
    @Data
    public static class MessageDTO {

        @JsonProperty("role")
        private String role;

        @JsonProperty("text")
        private String text;

        public void setRole(String role){
            this.role = role;
        }

        public void setText(String text){
            this.text = text;
        }

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

        public void setTotalTokens(int totalTokens){
            this.totalTokens = totalTokens;
        }

        public void setCompletionTokens(int completionTokens){
            this.completionTokens = completionTokens;
        }

        public void setInputTextTokens(int inputTextTokens){
            this.inputTextTokens = inputTextTokens;
        }

        public void setCost(double cost){
            this.cost = cost;
        }
    }
}
