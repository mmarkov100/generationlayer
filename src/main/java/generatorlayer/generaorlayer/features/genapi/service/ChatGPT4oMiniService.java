package generatorlayer.generaorlayer.features.genapi.service;

import generatorlayer.generaorlayer.features.genapi.config.GenApiConfig;
import generatorlayer.generaorlayer.features.genapi.dto.inbound.MessageStartDTO;
import generatorlayer.generaorlayer.features.genapi.dto.outbound.ResponseDTO;
import generatorlayer.generaorlayer.features.genapi.implementation.ChatGPT4oMiniResponseImpl;
import generatorlayer.generaorlayer.features.genapi.model.GenApiRequest;
import generatorlayer.generaorlayer.features.genapi.model.GenApiResponse;
import generatorlayer.generaorlayer.features.genapi.model.GenApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
public class ChatGPT4oMiniService implements GenApiService {

    private final RestTemplate restTemplate;
    private final GenApiConfig genApiConfig;

    @Autowired
    public ChatGPT4oMiniService(RestTemplate restTemplate, GenApiConfig genApiConfig) {
        this.restTemplate = restTemplate;
        this.genApiConfig = genApiConfig;
    }

    @Override
    public ResponseDTO convertToOutbound() {
        return null;
    }

    @Override
    public GenApiResponse generateResponse(GenApiRequest request) throws Exception {
        String apiKey = genApiConfig.getApiKey();
        String urlNetwork = genApiConfig.getUrlNetworks() + "/gpt-4o-mini";
        String urlGet = genApiConfig.getUrlGet() + "/";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept", "application/json");

        HttpEntity<GenApiRequest> entity = new HttpEntity<>(request, headers);

        // Отсылаем запрос на генерацию в сервис, получаем ответ, что он генерируется
        try {
            ResponseEntity<MessageStartDTO> responseStarted = restTemplate.exchange(urlNetwork, HttpMethod.POST, entity, MessageStartDTO.class);
            urlGet += Objects.requireNonNull(responseStarted.getBody()).getRequestId();
            HttpEntity<Void> onlyHeadersEntity = new HttpEntity<>(headers); // Пустое тело для GET-запроса

            while (true){
                ResponseEntity<ChatGPT4oMiniResponseImpl> response = restTemplate.exchange(urlGet, HttpMethod.GET, onlyHeadersEntity, ChatGPT4oMiniResponseImpl.class);
                if(Objects.equals(Objects.requireNonNull(response.getBody()).getStatus(), "error"))
                    throw new Exception("Error: " + response.getBody().getResult().get(0));
                else if (Objects.equals(Objects.requireNonNull(response.getBody()).getStatus(), "success"))
                    return response.getBody();  // Возвращаем тело ответа как ChatGPT4oMiniResponseImpl
                Thread.sleep(100); // Делаем перерыв между запросами
            }
        } catch (Exception e){
            throw new Exception(e.toString());
        }
    }
}
