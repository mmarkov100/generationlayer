package yandexgeneratorlayer.yandexgeneratorlayer.dto.inbound.FromYandexServer;

/*
Этот класс представляет структуру сообщения, возвращаемого Yandex API.
 */

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public class MessageResponseDTO {

    // указывает, от чьего имени сообщение
    @SerializedName("role")
    private String role;

    // содержит сгенерированный текст
    @SerializedName("text")
    private String text;
}
