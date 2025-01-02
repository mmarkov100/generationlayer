package yandexgeneratorlayer.yandexgeneratorlayer.dto.outbound.ToAPITextGenerator;

/*
Класс отвечает за составление ответа сообщения клиенту, а именно сообщения и его использованных токенов
 */

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseBackDTO {

    // Содержит роль и текст сообщения
    @SerializedName("message")
    private MessageResponseBackDTO message;

    // содержит информацию об использованных токенов, полученных и выполненных
    @SerializedName("usage")
    private UsageResponseBackDTO usage;
}
