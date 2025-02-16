# Message Generator

Это микросервис-прослойка между сервисами генераторов для генерации текста.

## Возможности

- **Генерация сообщений**: Позволяет генерировать сообщения от нейросети в режиме чата и промпт-режиме
- **Контекст**: возможно указать контекст сообщения
- **Смена нейросетей и температуры**: Возможность обращаться к разным доступным версиям YandexGPT и gen-api (Только ChatGPT4o-mini и DeepSeek-V3)

## Пример запроса YandexGPT Llama

Пример HTTP POST запроса на http://localhost:8085/api/yandex/process (должен быть указан хеддер с нужным API ключом этого сервиса)

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


## Пример запроса На DeepSeek-V3 и ChatGPT4o-mini
Пример HTTP POST запроса на http://localhost:8085/api/yandex/process (должен быть указан хеддер с нужным API ключом этого сервиса)
   ```JSON
{
  "model": "deepseek-v3",
  "messages": [
    {
      "content": "В конце каждого сообщения добавляй ЙОУ!!",
      "role": "system"
    },
    {
      "content": "Привет, как дела?",
      "role": "user"
    },
    {
      "content": "Отлично ЙОУ!!",
      "role": "assistant"
    },
    {
      "content": "Что делаешь?",
      "role": "user"
    }
  ],
  "temperature": 1
}
   ```

От чатгпт, тот же URL и хедер
   ```JSON
{
  "model": "chatgpt4o-mini",
  "messages": [
    {
      "role": "system",
      "content": [
        {
          "type": "text",
          "text": "В конце каждого сообщения добавляй ЙОУ!!"
        }
      ]
    },
    {
      "role": "user",
      "content": [
        {
          "type": "text",
          "text": "Сколько будет 2+3*5"
        }
      ]
    },
    {
      "role": "assistant",
      "content": [
        {
          "type": "text",
          "text": "Будет 17. ЙОУ!!"
        }
      ]
    },
    {
      "role": "user",
      "content": [
        {
          "type": "text",
          "text": "Это хорошо"
        }
      ]
    }
  ],
  "temperature": 0
}
   ```

## Ответ от сервиса
   ```JSON
{
    "message": {
        "role": "assistant",
        "text": "Отвечаю на твои вопросы, дружище ЙОУ!!"
    },
    "usage": {
        "totalTokens": 84,
        "completionTokens": 26,
        "inputTextTokens": 58,
        "cost": 0.0376
    }
}
   ```

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

Также еще указать ключ от GenApi https://gen-api.ru/
```java
package generatorlayer.generaorlayer.features.genapi.config;

import lombok.Getter;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
@Getter
public class GenApiConfig {

  private String apiKey = "someApiKey";
  private String urlNetworks = "https://api.gen-api.ru/api/v1/networks";
  private String urlGet = "https://api.gen-api.ru/api/v1/request/get";
}

```
Это нужно для доступа к генератору только от бэкенда
## Послесловие

Этот сервис часть моей курсовой работы, поэтому она будет еще дополняться со временем. Как минимум я планирую добавить:

- Другие нейросети для генерации текста
- Поддержка потока сообщений от нейросети, когда она генерирует сообщения
- Поддержку генерации картинок
- Рефакторинг кода