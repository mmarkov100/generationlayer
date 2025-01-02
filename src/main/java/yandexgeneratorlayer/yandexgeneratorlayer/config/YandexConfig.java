package yandexgeneratorlayer.yandexgeneratorlayer.config;

/*
Этот класс представляет конфигурационные свойства для работы с Yandex API.
Все переменные должны быть указаны в application.properties

Проверка переменных на присутствие в них данных.
В программе используется API-key который должен быть указан в окружении:
set YANDEX_API_KEY=<api-key> Ну или через настройки переменных окружения в IntellijIdea
Также надо указать папку:
set FOLDER_ID=<folderId> Ну или через настройки переменных окружения в IntellijIdea
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

    @NotBlank(message = "ApiKey не должен быть пустым")
    private String apiKey;

    @NotBlank(message = "Folder ID не должен быть пустым")
    private String folderId;

    @NotBlank(message = "API URL не должен быть пустым")
    private String apiUrl;

}
