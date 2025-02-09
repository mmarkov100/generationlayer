package generatorlayer.generaorlayer.features.yandexgpttext.service;

import generatorlayer.generaorlayer.features.yandexgpttext.config.YandexConfig;
import generatorlayer.generaorlayer.features.yandexgpttext.dto.inbound.YandexRequestTextGenerate;
import generatorlayer.generaorlayer.features.yandexgpttext.dto.inbound.YandexResponseResultDTO;
import generatorlayer.generaorlayer.features.yandexgpttext.dto.internal.YandexRequestTextInternal;
import generatorlayer.generaorlayer.features.yandexgpttext.dto.outbound.ChatRequestDTO;
import generatorlayer.generaorlayer.features.yandexgpttext.dto.outbound.YandexResponseTextGenerate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class YandexGPTService {

    private static final Logger logger = LoggerFactory.getLogger(YandexGPTService.class);

    private final RestTemplate restTemplate;

    @Autowired
    private YandexConfig yandexConfig;

    public YandexGPTService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Основной метод логики генерации
    public YandexResponseTextGenerate getYandexResponseTextGenerate( YandexRequestTextGenerate yandexRequestTextGenerate) {
        try {
            // Конвертируем в дто для преобразования
            YandexRequestTextInternal yandexRequestTextInternal = convertToInternal(yandexRequestTextGenerate);
            logger.debug("Converted to internal");

            // В запросе не может быть пустой контекст, поэтому добавляем проверку на пустоту и добавляем в контекст обычный пробел
            if (Objects.equals(yandexRequestTextInternal.getMessages().get(0).getText(), "")) {
                yandexRequestTextInternal.getMessages().get(0).setText(" ");
                logger.debug("Context is null");
            }

            // Проверка на существование модели от Яндекса и установка maxTokens
            int maxTokens = switch (yandexRequestTextInternal.getModelUri()) {
                case "yandexgpt-32k/latest" -> 32000;
                case "yandexgpt-lite/latest", "yandexgpt/latest", "llama/latest", "llama-lite/latest" -> 8000;
                default -> {
                    logger.error("ERROR: This model does not exist");
                    throw new RuntimeException("This model does not exist");
                }
            };

            // Обновляем корректность модели
            String modelUri = "gpt://" + yandexConfig.getFolderId() + "/" +yandexRequestTextInternal.getModelUri();
            yandexRequestTextInternal.setModelUri(modelUri);
            logger.debug("Selected modelUri: {}", yandexRequestTextInternal.getModelUri());

            // Считаем кол-во токенов, которое сейчас есть в чате
            int totalTokens = countTokens(yandexRequestTextInternal.getMessages(), yandexConfig);

            // Удаляем старые сообщения, пока токены превышают лимит
            while (totalTokens > maxTokens && yandexRequestTextInternal.getMessages().size() > 2) {
                logger.info("Total tokens: {}. Removing oldest message pair.", totalTokens);

                // Удаляем первое пользовательское сообщение и его ответ
                Iterator<YandexRequestTextInternal.Message> iterator = yandexRequestTextInternal.getMessages().iterator();

                while (iterator.hasNext()) {
                    YandexRequestTextInternal.Message message = iterator.next();
                    if ("user".equalsIgnoreCase(message.getRole())) {
                        iterator.remove(); // Удаляем сообщение пользователя
                        if (iterator.hasNext()) {
                            iterator.next();
                            iterator.remove(); // Удаляем сообщение нейросети
                        }
                        break;
                    }
                }

                // Пересчитываем токены
                totalTokens = countTokens(yandexRequestTextInternal.getMessages(), yandexConfig);
            }

            // Если все еще превышает лимит — бросаем исключение
            if (totalTokens > maxTokens) {
                logger.error("Input exceeds token limit of {}", maxTokens);
                throw new IllegalArgumentException("Input exceeds token limit of " + maxTokens);
            }

            // Конвертируем в ДТО для отправки в яндекс
            ChatRequestDTO chatRequestDTO = convertToOutbound(yandexRequestTextInternal);
            logger.info("Converted to DTO");

            // Отправляем запрос на яндекс сервер и получаем ответ
            YandexResponseResultDTO yandexResponseResultDTO = sendRequestToYandexGPT(chatRequestDTO);
            logger.info("Response from Yandex: {}", yandexResponseResultDTO.getResult().getAlternatives().get(0).getMessage().getText());

            return convertToOutbound(yandexResponseResultDTO);

        } catch (Exception e) {
            logger.error("ERROR: {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    // Метод для отправки запроса генерации текста на яндекс сервер
    private YandexResponseResultDTO sendRequestToYandexGPT(ChatRequestDTO chatRequestDTO) throws Exception {
        String apiKey = yandexConfig.getApiKey();
        String folderId = yandexConfig.getFolderId();
        String url = yandexConfig.getApiUrl();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Api-Key " + apiKey);
        headers.set("x-folder-id", folderId);

        HttpEntity<ChatRequestDTO> entity = new HttpEntity<>(chatRequestDTO, headers);

        ResponseEntity<YandexResponseResultDTO> response = restTemplate.exchange(url, HttpMethod.POST, entity, YandexResponseResultDTO.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new Exception("Failed to get response from Yandex API: " + response.getStatusCode());
        }
    }


    // Примерный подсчет токенов по количеству слов
    private static int countTokens(List<YandexRequestTextInternal.Message> messages, YandexConfig yandexConfig) {
        final double TOKEN_MODIFIER = yandexConfig.getTokenModifier();

        int tokens = 0;
        for (YandexRequestTextInternal.Message message : messages) {
            tokens += message.getText().split("\\s+").length; // Разделение по пробелам
        }
        logger.info("Tokens counted: {}", tokens * TOKEN_MODIFIER);
        return (int) (tokens*2.3);
    }

    public static YandexResponseTextGenerate convertToOutbound(YandexResponseResultDTO inboundDto) {
        if (inboundDto == null || inboundDto.getResult() == null || inboundDto.getResult().getAlternatives().isEmpty()) {
            return null;
        }

        YandexResponseTextGenerate outboundDto = new YandexResponseTextGenerate();

        YandexResponseResultDTO.Alternative firstAlternative = inboundDto.getResult().getAlternatives().get(0);
        YandexResponseTextGenerate.MessageDTO messageDTO = new YandexResponseTextGenerate.MessageDTO();
        messageDTO.setRole(firstAlternative.getMessage().getRole());
        messageDTO.setText(firstAlternative.getMessage().getText());
        outboundDto.setMessage(messageDTO);

        YandexResponseResultDTO.Usage usage = inboundDto.getResult().getUsage();
        YandexResponseTextGenerate.UsageDTO usageDTO = new YandexResponseTextGenerate.UsageDTO();
        usageDTO.setTotalTokens(Integer.parseInt(usage.getTotalTokens()));
        usageDTO.setCompletionTokens(Integer.parseInt(usage.getCompletionTokens()));
        usageDTO.setInputTextTokens(Integer.parseInt(usage.getInputTextTokens()));
        outboundDto.setUsage(usageDTO);

        return outboundDto;
    }

    public static ChatRequestDTO convertToOutbound(YandexRequestTextInternal internalDto) {
        if (internalDto == null) {
            return null;
        }

        ChatRequestDTO chatRequestDTO = new ChatRequestDTO();
        chatRequestDTO.setModelUri(internalDto.getModelUri());
        chatRequestDTO.setCompletionOptions(new ChatRequestDTO.CompletionOptions(
                internalDto.getCompletionOptions().isStream(),
                internalDto.getCompletionOptions().getMaxTokens(),
                internalDto.getCompletionOptions().getTemperature(),
                new ChatRequestDTO.CompletionOptions.ReasoningOptions(
                        internalDto.getCompletionOptions().getReasoningOptions().getMode()
                )

        ));
        chatRequestDTO.setMessages(
                internalDto.getMessages().stream()
                        .map(msg -> {
                            ChatRequestDTO.Message message = new ChatRequestDTO.Message();
                            message.setRole(msg.getRole());
                            message.setText(msg.getText());
                            return message;
                        })
                        .collect(Collectors.toList())
        );

        return chatRequestDTO;
    }

    // Метод конвертации во внутренний класс
    private static YandexRequestTextInternal convertToInternal(YandexRequestTextGenerate inboundDto) {
        if (inboundDto == null) {
            return null;
        }

        return new YandexRequestTextInternal(
                inboundDto.getModelUri(),
                new YandexRequestTextInternal.CompletionOptions(
                        inboundDto.getCompletionOptions().isStream(),
                        inboundDto.getCompletionOptions().getMaxTokens(),
                        inboundDto.getCompletionOptions().getTemperature(),
                        new YandexRequestTextInternal.CompletionOptions.ReasoningOptions(
                                inboundDto.getCompletionOptions().getReasoningOptions().getMode()
                        )
                ),
                inboundDto.getMessages().stream()
                        .map(msg -> new YandexRequestTextInternal.Message(msg.getRole(), msg.getText()))
                        .collect(Collectors.toList())
        );
    }
}
