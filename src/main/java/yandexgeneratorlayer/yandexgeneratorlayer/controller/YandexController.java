package yandexgeneratorlayer.yandexgeneratorlayer.controller;

/*
Класс контроллера для яндекс-генерации
 */

import com.google.gson.Gson;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yandexgeneratorlayer.yandexgeneratorlayer.dto.inbound.FromAPITextGenerator.YaChatDTO;
import yandexgeneratorlayer.yandexgeneratorlayer.service.YandexProcessingService;

import java.util.Map;

@RestController
@RequestMapping("/api/yandex")
public class YandexController {

    private static final Logger logger = LoggerFactory.getLogger(YandexController.class);

    private final YandexProcessingService processingService;

    public YandexController(YandexProcessingService processingService) {
        this.processingService = processingService;
    }



    // Документация swagger, доступна по ссылке http://localhost:8082/swagger-ui/index.html#/
    @Operation(summary = "Обработка запроса к Yandex API",
            description = "Обрабатывает запрос с параметрами для генерации текста")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный ответ",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class),
                            examples = @ExampleObject(name = "SuccessfulResponse",
                                    value = """
                                            {
                                                "role": "assistant",
                                                "text": "Это пример успешного ответа",
                                                "usage": {
                                                    "completionTokens": 23,
                                                    "inputTextTokens": 52,
                                                    "totalTokens": 75
                                                }
                                            }"""))),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "ValidationError",
                                    value = "{\"error\": \"Validation failed\", \"message\": \"Model URI must not be null\"}"))),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(name = "ServerError",
                                    value = "{\"error\": \"Internal Server Error\", \"message\": \"Unexpected error occurred\"}")))
    })
    @PostMapping("/process")
    public ResponseEntity<?> processYandexRequest(
            @io.swagger.v3.oas.annotations.parameters.RequestBody (description = "Данные для генерации текста",
                    required = true,
                    content = @Content(schema = @Schema(implementation = YaChatDTO.class)))
            @Valid  @RequestBody YaChatDTO chatDTO) {
        try {
            // Вывод пришедших сообщений в логгер
            logger.info("Received JSON: {}", new Gson().toJson(chatDTO));

            // Делегируем обработку в сервис
            Map<String, Object> jsonResponse = processingService.processChat(chatDTO);

            logger.info("Response: {}", jsonResponse);
            return ResponseEntity.ok(jsonResponse);

        } catch (Exception e) {
            logger.error("Error processing Yandex request", e);
            return ResponseEntity.status(500).body(Map.of(
                    "error", "Error processing Yandex request",
                    "message", e.getMessage()
            ));
        }
    }
}
