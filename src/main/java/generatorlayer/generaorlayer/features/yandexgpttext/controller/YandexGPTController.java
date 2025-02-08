package generatorlayer.generaorlayer.features.yandexgpttext.controller;

import generatorlayer.generaorlayer.features.yandexgpttext.dto.inbound.YandexRequestTextGenerate;
import generatorlayer.generaorlayer.features.yandexgpttext.dto.outbound.YandexResponseTextGenerate;
import generatorlayer.generaorlayer.features.yandexgpttext.service.YandexGPTService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/yandex")
public class YandexGPTController {

    private static final Logger logger = LoggerFactory.getLogger(YandexGPTController.class);

    private final YandexGPTService yandexGPTService;

    public YandexGPTController(YandexGPTService yandexGPTService) {
        this.yandexGPTService = yandexGPTService;
    }

    // Запрос на генерацию текста с YandexGPT
    @PostMapping("/process")
    public ResponseEntity<?> generateYandexGPT(@Valid @RequestBody YandexRequestTextGenerate yandexRequestTextGenerate) {
        try {
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
