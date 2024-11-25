package yandexgeneratorlayer.yandexgeneratorlayer.dto.internal;

/*
Этот класс описывает сообщение во внутреннем формате приложения
 */

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageInternalDTO {

    private String role; // Указывает роль сообщения (user, assistant, system)
    private String text; // Содержит текст сообщения

    public MessageInternalDTO(String role, String text) {
        this.role = role;
        this.text = text;
    }
}
