package yandexgeneratorlayer.yandexgeneratorlayer.dto.outbound.ToYandexServer;

/*
Этот класс описывает структуру сообщения, отправляемого в Yandex API
 */

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import yandexgeneratorlayer.yandexgeneratorlayer.dto.internal.MessageInternalDTO;

@Getter
@Setter
public class MessageToJSON {

    // Указывает роль сообщения (user, assistant, system)
    @SerializedName("role")
    private String role;

    // Содержит текст сообщения
    @SerializedName("text")
    private String text;

    public MessageToJSON(MessageInternalDTO messageService) {
        this.role = messageService.getRole();
        this.text = messageService.getText();
    }
}
