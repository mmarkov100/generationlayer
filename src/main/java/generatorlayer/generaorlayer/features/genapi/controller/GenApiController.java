package generatorlayer.generaorlayer.features.genapi.controller;

import generatorlayer.generaorlayer.core.config.ApiConfig;
import generatorlayer.generaorlayer.features.genapi.config.GenApiConfig;
import generatorlayer.generaorlayer.features.genapi.dto.inbound.ChatGPT4oMiniRequestImpl;
import generatorlayer.generaorlayer.features.genapi.dto.inbound.DeepSeekV3RequestImpl;
import generatorlayer.generaorlayer.features.genapi.dto.inbound.GenApiRequest;
import generatorlayer.generaorlayer.features.genapi.dto.outbound.GenApiResponse;
import generatorlayer.generaorlayer.features.genapi.service.GenApiService;
import generatorlayer.generaorlayer.features.genapi.service.ChatGPT4oMiniGenApiService;
import generatorlayer.generaorlayer.features.genapi.service.DeepSeekV3GenApiService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/genapi")
public class GenApiController {

    private static final Logger logger = LoggerFactory.getLogger(GenApiController.class);

    @Autowired
    private ApiConfig apiConfig;

    @Autowired
    private GenApiConfig genApiConfig;

    @Autowired
    private ChatGPT4oMiniGenApiService chatGPT4oMiniService;

    @Autowired
    private DeepSeekV3GenApiService deepSeekV3Service;

    // Эндпоинт на генерацию ген-апи, сейчас либо дипсик, либо чатгпт4омини делаю
    @PostMapping("/generate")
    public ResponseEntity<?> generateYandexGPT(@Valid @RequestHeader String apiGeneratorKey,
                                               @RequestBody GenApiRequest genApiRequest) {
        try {
            if (!Objects.equals(apiGeneratorKey, apiConfig.getApiGenerationKey())) { // Условие на проверку, правильный ли указан ключ для доступа к этому микросервису
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Map.of(
                                "error", "Wrong apiGenKey"
                        ));
            }
            // Проверяем модель и выбираем соответствующий сервис
            GenApiService genApiService = selectGenApiService(genApiRequest);

            GenApiResponse genApiResponse = genApiService.generateResponse(genApiRequest); // Генерируем ответ от нейросети

            return ResponseEntity.ok(genApiService.convertToOutbound(genApiResponse)); // Возвращаем нужный формат
        } catch (Exception e) {
            logger.error("Error processing GenAPI request: {}", e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "error", "Error processing request",
                            "message", e.getMessage()
                    ));
        }
    }


    // Метод для выбора подходящего сервиса в зависимости от модели
    private GenApiService selectGenApiService(GenApiRequest genApiRequest) {
        if (genApiRequest instanceof ChatGPT4oMiniRequestImpl) {
            return chatGPT4oMiniService;
        } else if (genApiRequest instanceof DeepSeekV3RequestImpl) {
            return deepSeekV3Service;
        }
        throw new IllegalArgumentException("Unsupported model");
    }
}
