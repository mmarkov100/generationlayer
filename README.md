# Message Generator

Это микросервис-прослойка между сервисами яндекса для генерации текста.

## Возможности

- **Генерация сообщений**: Позволяет генерировать сообщения от нейросети в режиме чата и промпт-режиме
- **Контекст**: возможно указать контекст сообщения 
- **Смена нейросетей и температуры**: Возможность обращаться к разным доступным версиям YandexGPT
- **Swagger-документация**: http://localhost:8082/swagger-ui/index.html#/

## Пример запроса

Пример HTTP POST запроса на http://localhost:8082/api/yandex/process:

   ```JSON
{
  "modelUri": "llama/latest",
  "completionOptions": {
    "stream": false,
    "maxTokens": 2000,
    "temperature": 1
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

modelUri - Указание модели для генерации, сейчас только YandexGPT модели, указаны по ссылке
https://yandex.cloud/ru/docs/foundation-models/concepts/yandexgpt/models?utm_referrer=https%3A%2F%2Fyndx.auth.yandex.cloud%2F

- stream - Режим отправки сообщения с генерацией в реальном времени. Пока что недоступно, поэтому всегда false

- maxTokens - Максимальное кол-во токенов, которые будут использованы для генерации, максимум 2000 токенов

- temperature - Указать число, от 0 до 1, насколько сильно нейросеть может отходить от точного ответа

- messages - История сообщения между пользователем и нейросети. 
Первое сообщение всегда контекст чата


## Ответ от сервиса

   ```JSON
{
  "message": {
    "role": "assistant",
    "text": "Я модель искусственного интеллекта YandexGPT 2."
  },
  "usage": {
    "totalTokens": 54,
    "completionTokens": 10,
    "inputTextTokens": 44
  }
}
   ```

- usage - Количество токенов было потрачено при создании этого сообщения


Чтобы пользоваться вам нужно указать свой API ключ для генерации в application.properites, вот пример

   ```
   server.port=8082
spring.application.name=YandexGeneratorLayer


# This application is not needed for database
spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration

# Swagger API documentation
springdoc.api-docs.enabled=true
springdoc.api-docs.path=/v3/api-docs
springdoc.swagger-ui.enabled=true
springdoc.swagger-ui.path=/swagger-ui.html


yandex.api.api-url=https://llm.api.cloud.yandex.net/foundationModels/v1/completion
yandex.api.api-key=${YANDEX_API_KEY}
yandex.api.folder-id=${FOLDER_ID}

   ```
- Вместо YANDEX_API_KEY ваш ключ
- Вместо FOLDER_ID также folder_id, который можно узнать из личного кабинета Yandex Cloud