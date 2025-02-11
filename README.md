# Message Generator

Это микросервис-прослойка между сервисами яндекса для генерации текста.

## Возможности

- **Генерация сообщений**: Позволяет генерировать сообщения от нейросети в режиме чата и промпт-режиме
- **Контекст**: возможно указать контекст сообщения
- **Смена нейросетей и температуры**: Возможность обращаться к разным доступным версиям YandexGPT

## Пример запроса

Пример HTTP POST запроса на http://localhost:8085/api/yandex/process

   ```JSON
{
  "modelUri": "llama/latest",
  "completionOptions": {
    "stream": false,
    "maxTokens": 2000,
    "temperature": 1,
    "reasoningOptions": {
      "mode": "DISABLED"
    }
  },
  "messages": [
    {
      "role": "system",
      "text": " "
    },
    {
      "role": "user",
      "text": "Как дела?"
    },
    {
      "role": "assistant",
      "text": "У меня всё хорошо. Чем могу помочь?"
    },
    {
      "role": "user",
      "text": "Что ты за модель, какое у тебя название?"
    }
  ]
}
   ```

- modelUri - Указание модели для генерации, сейчас только YandexGPT модели, указаны по ссылке
https://yandex.cloud/ru/docs/foundation-models/concepts/yandexgpt/models?utm_referrer=https%3A%2F%2Fyndx.auth.yandex.cloud%2F

- stream - Режим отправки сообщения с генерацией в реальном времени. Пока что недоступно, поэтому всегда false

- maxTokens - Максимальное кол-во токенов, которые будут использованы для генерации, максимум 2000 токенов

- temperature - Указать число, от 0 до 1, насколько сильно нейросеть может отходить от точного ответа

- reasoningOptions - Может ли нейросеть "размышлять" во время ответа. Может быть только DISABLED или ENABLED_HIDDEN

- messages - История сообщения между пользователем и нейросети.
  Первое сообщение всегда контекст чата


## Ответ от сервиса

   ```JSON
{
  "message": {
    "role": "assistant",
    "text": "Я - это модель LLaMA, разработанная компанией Meta. LLaMA расшифровывается как \"Large Language Model Meta AI\". Я являюсь искусственным интеллектом, обученным на большом объёме текстовых данных и способным понимать и генерировать человеческий язык."
  },
  "usage": {
    "totalTokens": 119,
    "completionTokens": 69,
    "inputTextTokens": 50
  }
}
   ```

- usage - Количество токенов было потрачено при создании этого сообщения


## Как пользоваться

Сначала скопируйте репозиторий

   ```BASH
   git clone https://github.com/mmarkov100/generationlayer
   ```

Чтобы пользоваться вам нужно указать свой API ключ для генерации в файле YandexConfig, который надо создать по пути src/main/java/generatorlayer/generaorlayer/features/yandexgpttext/config/YandexConfig.java

Ниже приведен пример кода файла

   ```JAVA
package generatorlayer.generaorlayer.features.yandexgpttext.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Setter
@Getter
@ConfigurationProperties(prefix = "yandex.api")
@Component
@Validated
public class YandexConfig {
    
    private String apiKey = "someApiKey";
    
    private String folderId = "someFolderId";
    
    private String apiUrl = "https://llm.api.cloud.yandex.net/foundationModels/v1/completion";

    private double tokenModifier = 2.3;
}


   ```
- Присвойте apiKey значение вашего ключа, также с папкой. Как получить ключ https://yandex.cloud/ru/docs/foundation-models/api-ref/authentication

Также надо создать файл по директории src/main/java/generatorlayer/generaorlayer/core/config/ApiConfig.java
```java
package redslicedatabase.redslicedatabase.config;

import lombok.Getter;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
@Getter
public class ApiConfig {

   private final String apiGenerationKey = "someApiKey";
}
```
Это нужно для доступа к генератору только от бэкенда
## Послесловие

Этот сервис часть моей курсовой работы, поэтому она будет еще дополняться со временем. Как минимум я планирую добавить:

- Другие нейросети для генерации текста
- Поддержка потока сообщений от нейросети, когда она генерирует сообщения
- Поддержку генерации картинок