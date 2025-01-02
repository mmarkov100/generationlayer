package yandexgeneratorlayer.yandexgeneratorlayer.dto.outbound.ToAPITextGenerator;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageResponseBackDTO {

    // указывает, от чьего имени сообщение
    @SerializedName("role")
    private String role;

    // содержит сгенерированный текст
    @SerializedName("text")
    private String text;
}
