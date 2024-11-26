package yandexgeneratorlayer.yandexgeneratorlayer.dto.inbound.FromYandexServer;

/*
Этот класс описывает информацию об использовании токенов, возвращаемую Yandex API.
 */

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public class UsageDTO {

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
