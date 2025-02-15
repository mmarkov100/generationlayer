package generatorlayer.generaorlayer.features.genapi.service;

import generatorlayer.generaorlayer.features.genapi.config.GenApiConfig;
import generatorlayer.generaorlayer.features.genapi.dto.inbound.MessageStartDTO;
import generatorlayer.generaorlayer.features.genapi.dto.outbound.ResponseGenApiDTO;
import generatorlayer.generaorlayer.features.genapi.dto.outbound.ChatGPT4oMiniResponseImpl;
import generatorlayer.generaorlayer.features.genapi.interfaces.GenApiRequest;
import generatorlayer.generaorlayer.features.genapi.interfaces.GenApiResponse;
import generatorlayer.generaorlayer.features.genapi.interfaces.GenApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
public class ChatGPT4oMiniService implements GenApiService {

    private static final Logger logger = LoggerFactory.getLogger(ChatGPT4oMiniService.class);

    private final RestTemplate restTemplate;
    private final GenApiConfig genApiConfig;

    @Autowired
    public ChatGPT4oMiniService(RestTemplate restTemplate, GenApiConfig genApiConfig) {
        this.restTemplate = restTemplate;
        this.genApiConfig = genApiConfig;
    }

    // Метод конвертации для отправки из сервиса
    @Override
    public ResponseGenApiDTO convertToOutbound(GenApiResponse genApiResponse) throws Exception {
        // Создаем новый объект ResponseGenApiDTO
        ResponseGenApiDTO responseGenApiDTO = new ResponseGenApiDTO();

        // Инициализируем message, если оно еще не было инициализировано
        if (responseGenApiDTO.getMessage() == null) {
            responseGenApiDTO.setMessage(new ResponseGenApiDTO.MessageDTO());
        }

        responseGenApiDTO.getMessage().setRole(genApiResponse.getRole());
        responseGenApiDTO.getMessage().setText(genApiResponse.getText());

        // Инициализируем usage, если оно еще не было инициализировано
        if (responseGenApiDTO.getUsage() == null) {
            responseGenApiDTO.setUsage(new ResponseGenApiDTO.UsageDTO());
        }

        responseGenApiDTO.getUsage().setCompletionTokens(genApiResponse.getCompletionTokens());
        responseGenApiDTO.getUsage().setTotalTokens(genApiResponse.getAllTokens());
        responseGenApiDTO.getUsage().setInputTextTokens(genApiResponse.getUsageTokens());
        responseGenApiDTO.getUsage().setCost(genApiResponse.getCost());

        logger.info("Convert to output: {}", responseGenApiDTO);
        return responseGenApiDTO;
    }

    // Метод для получения сообщения от нейросети
    @Override
    public GenApiResponse generateResponse(GenApiRequest request) throws Exception {
        String apiKey = genApiConfig.getApiKey();
        String urlNetwork = genApiConfig.getUrlNetworks() + "/gpt-4o-mini";
        String urlGet = genApiConfig.getUrlGet() + "/";

        // Обозначаем хеддеры для запроса
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept", "application/json");

        HttpEntity<GenApiRequest> entity = new HttpEntity<>(request, headers);

        // Отсылаем запрос на генерацию в сервис, получаем ответ, что он генерируется
        try {
            ResponseEntity<MessageStartDTO> responseStarted = restTemplate.exchange(urlNetwork, HttpMethod.POST, entity, MessageStartDTO.class);
            logger.info("Response ID: {}", responseStarted);
            urlGet += Objects.requireNonNull(responseStarted.getBody()).getRequestId();
            HttpEntity<Void> onlyHeadersEntity = new HttpEntity<>(headers); // Пустое тело для GET-запроса

            while (true){
                ResponseEntity<ChatGPT4oMiniResponseImpl> response = restTemplate.exchange(urlGet, HttpMethod.GET, onlyHeadersEntity, ChatGPT4oMiniResponseImpl.class);
                if(Objects.equals(Objects.requireNonNull(response.getBody()).getStatus(), "error")) {
                    logger.error("Error, response: {}", response);
                    throw new Exception("Error: " + response.getBody().getResult().get(0));
                }
                else if (Objects.equals(Objects.requireNonNull(response.getBody()).getStatus(), "success")) {
                    logger.error("Return response: {}", response);
                    return response.getBody();  // Возвращаем тело ответа как ChatGPT4oMiniResponseImpl
                }
                Thread.sleep(100); // Делаем перерыв между запросами
            }
        } catch (Exception e){
            throw new Exception(e.toString());
        }
    }
}
