package yandexgeneratorlayer.yandexgeneratorlayer.dto.outbound.ToYandexServer.enums;

/*
Этот enum представляет допустимые роли для сообщений в JSON-запросах.

user: Инициирует запрос.
assistant: Отвечает на запрос.
system: Задаёт правила взаимодействия.
 */

import lombok.Getter;

@Getter
public enum RoleToJSON {
    USER("user"),
    ASSISTANT("assistant"),
    SYSTEM("system");

    private final String role;

    RoleToJSON(String role) {
        this.role = role;
    }
}
