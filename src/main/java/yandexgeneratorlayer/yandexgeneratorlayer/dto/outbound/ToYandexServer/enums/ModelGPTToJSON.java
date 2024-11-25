package yandexgeneratorlayer.yandexgeneratorlayer.dto.outbound.ToYandexServer.enums;

/*
Этот enum представляет допустимые значения моделей генерации текста для формирования JSON-запросов.
Про каждую модель можно прочитать на https://yandex.cloud/ru/docs/foundation-models/concepts/yandexgpt/models
Если кратко - lite для промпт-апросов, без lite-приписки в режиме чата.
 */

import lombok.Getter;

@Getter
public enum ModelGPTToJSON {
    YANDEXGPT_RC("yandexgpt/rc"),
    YANDEXGPT_LATEST("yandexgpt/latest"),
    YANDEXGPT_32K_RC("yandexgpt-32k/rc"),
    YANDEXGPT_LITE_RC("yandexgpt-lite/rc"),
    YANDEXGPT_LITE_LATEST("yandexgpt-lite/latest");

    private final String modelGPT;

    ModelGPTToJSON(String uri) {
        this.modelGPT = uri;
    }

}
