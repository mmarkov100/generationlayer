package yandexgeneratorlayer.yandexgeneratorlayer.dto.inbound.FromAPITextGenerator;

/*
Класс YaChatDTO чётко описывает структуру входящих данных
 */

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class YaMessageDTO {

    // роль отправителя сообщения.
    // user — предназначена для отправки пользовательских сообщений к модели.
    // system — позволяет задать контекст запроса и определить поведение модели.
    // assistant — используется для ответов, которые генерирует модель
    @JsonProperty("role")
    @NotNull(message = "Role must not be null")
    private String role;

    // текстовое содержимое сообщения
    @JsonProperty("text")
    @NotNull(message = "Text must not be null")
    @Size(min = 1, max = 2000, message = "Text length must be between 1 and 2000 characters") //Задаем границу сообщения в 2к символов
    private String text;
}
