package yandexgeneratorlayer.yandexgeneratorlayer.dto.inbound.FromYandexServer;

/*
Этот класс описывает структуру поля result из JSON-ответа.
 */

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public class ResultDTO {

    // содержит возможные альтернативы текста
    @SerializedName("alternatives")
    private AlternativeDTO[] alternatives;

    // содержит метаинформацию об использовании ресурсов
    @SerializedName("usage")
    private UsageDTO usage;

}
