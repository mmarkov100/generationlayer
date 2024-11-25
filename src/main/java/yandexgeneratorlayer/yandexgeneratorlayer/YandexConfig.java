package yandexgeneratorlayer.yandexgeneratorlayer;

/*
Этот класс представляет конфигурационные свойства для работы с Yandex API.
Все переменные должны быть указаны в application.properties
 */

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;

@Setter
@Getter
@ConfigurationProperties(prefix = "yandex.api")
@Validated
public class YandexConfig {

    // Проверка переменных на присутствие в них данных

    @NotBlank(message = "IAM Токен не должен быть пустым")
    private String iamToken;

    @NotBlank(message = "Folder ID не должен быть пустым")
    private String folderId;

    @NotBlank(message = "API URL не должен быть пустым")
    private String apiUrl;

}
