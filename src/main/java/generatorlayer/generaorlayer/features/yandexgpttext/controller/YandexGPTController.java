package generatorlayer.generaorlayer.features.yandexgpttext.controller;

import generatorlayer.generaorlayer.core.config.ApiConfig;
import generatorlayer.generaorlayer.features.yandexgpttext.dto.inbound.YandexRequestTextGenerate;
import generatorlayer.generaorlayer.features.yandexgpttext.dto.outbound.YandexResponseTextGenerate;
import generatorlayer.generaorlayer.features.yandexgpttext.service.YandexGPTService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/yandex")
public class YandexGPTController {

    private static final Logger logger = LoggerFactory.getLogger(YandexGPTController.class);

    @Autowired
    private ApiConfig apiConfig;

    private final YandexGPTService yandexGPTService;

    public YandexGPTController(YandexGPTService yandexGPTService) {
        this.yandexGPTService = yandexGPTService;
    }

    // Запрос на генерацию текста с YandexGPT
    @PostMapping("/process")
    public ResponseEntity<?> generateYandexGPT(@Valid @RequestHeader String apiGeneratorKey,
                                               @RequestBody YandexRequestTextGenerate yandexRequestTextGenerate) {
        try {
            if (!Objects.equals(apiGeneratorKey, apiConfig.getApiGenerationKey())) {
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Map.of(
                                "error", "Wrong apiGenKey"
                        ));
            }
            logger.info("Generate Yandex GPT text");
            YandexResponseTextGenerate responseDto = yandexGPTService.getYandexResponseTextGenerate(yandexRequestTextGenerate);
            return ResponseEntity.ok(responseDto);
        } catch (Exception e) {
            logger.error("Error processing Yandex request: {}", e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "error", "Error processing Yandex request",
                            "message", e.getMessage()
                    ));
        }
    }
}
