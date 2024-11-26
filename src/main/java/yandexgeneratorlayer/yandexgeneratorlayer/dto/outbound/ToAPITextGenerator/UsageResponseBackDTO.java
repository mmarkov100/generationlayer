package yandexgeneratorlayer.yandexgeneratorlayer.dto.outbound.ToAPITextGenerator;

/*
Класс для отправки клиенту количество использованных токенов для генерации текста
 */

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsageResponseBackDTO {

    //количество токенов, использованных для входного текст
    @SerializedName("inputTextTokens")
    private int inputTextTokens;

    // токены, использованные для генерации текста
    @SerializedName("completionTokens")
    private int completionTokens;

    // общее количество токенов, включающее входной текст и сгенерированный результат
    @SerializedName("totalTokens")
    private int totalTokens;
}
