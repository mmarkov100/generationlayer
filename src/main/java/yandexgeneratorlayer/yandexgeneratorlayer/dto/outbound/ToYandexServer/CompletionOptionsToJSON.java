package yandexgeneratorlayer.yandexgeneratorlayer.dto.outbound.ToYandexServer;

/*
Этот класс описывает параметры генерации текста для формирования JSON-запроса.
 */

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import yandexgeneratorlayer.yandexgeneratorlayer.dto.internal.CompletionInternalDTO;

@Getter
@Setter
public class CompletionOptionsToJSON {

    // Определяет, требуется ли потоковая обработка
    @SerializedName("stream")
    private boolean stream;

    // Максимальное количество токенов в сгенерированном ответе
    @SerializedName("maxTokens")
    private int maxTokens;

    // Контролирует уровень случайности генерации текста
    @SerializedName("temperature")
    private double temperature;

    // Инициализирует объект на основе CompletionInternalDTO, берет все нужные параметры
    public CompletionOptionsToJSON(CompletionInternalDTO completionOptions) {
        this.stream = completionOptions.isStream();
        this.maxTokens = completionOptions.getMaxTokens();
        this.temperature = completionOptions.getTemperature();
    }
}
