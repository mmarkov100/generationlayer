package yandexgeneratorlayer.yandexgeneratorlayer.dto.inbound.FromYandexServer;

/*
Этот класс представляет одну альтернативу из массива alternatives в ответе Yandex API.
 */

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public class AlternativeDTO {

    // Описывает сообщение, сгенерированное API
    @SerializedName("message")
    private MessageResponseDTO message;

    // отражает статус конкретной альтернативы (например, "ALTERNATIVE_STATUS_PARTIAL", "ALTERNATIVE_STATUS_FINAL")
    @SerializedName("status")
    private String status;

}
