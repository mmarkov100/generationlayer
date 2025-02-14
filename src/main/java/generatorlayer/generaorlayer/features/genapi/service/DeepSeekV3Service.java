package generatorlayer.generaorlayer.features.genapi.service;

import generatorlayer.generaorlayer.features.genapi.implementation.ChatGPT4oMiniRequestImpl;
import org.springframework.stereotype.Service;

@Service
public class DeepSeekV3Service {

    public String generateResponse(ChatGPT4oMiniRequestImpl request) {
        // Логика отправки запроса к ChatGPT и обработки ответа
        // Примерный запрос через RestTemplate или WebClient

        return "Ответ от ChatGPT";
    }
}
