package yandexgeneratorlayer.yandexgeneratorlayer.controller;

import com.google.gson.Gson;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
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


    @Operation(summary = "Обработка запроса к Yandex API",
            description = "Обрабатывает запрос с параметрами для генерации текста")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный ответ"),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
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
            Map<String, String> jsonResponse = processingService.processChat(chatDTO);

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
