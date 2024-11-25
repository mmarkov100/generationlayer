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
    private String inputTextTokens;

    // токены, использованные для генерации текста
    @SerializedName("completionTokens")
    private String completionTokens;

    // общее количество токенов, включающее входной текст и сгенерированный результат
    @SerializedName("totalTokens")
    private String totalTokens;

}
