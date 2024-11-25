package yandexgeneratorlayer.yandexgeneratorlayer.dto.internal;

/*
Этот класс описывает внутренние параметры генерации текста.
 */

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompletionInternalDTO {

    private boolean stream; // Указывает, требуется ли потоковая обработка
    private int maxTokens; // Максимальное количество токенов для генерации
    private double temperature; // Определяет уровень случайности в генерации

    public CompletionInternalDTO(boolean stream, int maxTokens, double temperature) {
        this.stream = stream;
        this.maxTokens = maxTokens;
        this.temperature = temperature;
    }
}
