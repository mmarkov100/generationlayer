package yandexgeneratorlayer.yandexgeneratorlayer.dto.inbound.FromYandexServer;

/*
Этот класс представляет общий ответ от Yandex API
 */

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public class ResponseDTO {
    @SerializedName("result")
    private ResultDTO result;

}