package yandexgeneratorlayer.yandexgeneratorlayer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import yandexgeneratorlayer.yandexgeneratorlayer.dto.inbound.FromAPITextGenerator.YaChatDTO;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class YaChatDTOTest {

    @Test
    public void testDeserializeJson() throws Exception {
        String json = """
                {
                  "modelUri": "yandexgpt/rc",
                  "completionOptions": {
                    "stream": false,
                    "maxTokens": 2000,
                    "temperature": 0.8
                  },
                  "messages": [
                    {
                      "role": "system",
                      "text": "Твой лимит на ответ от 100 до 300 символов, укладывайся в это значение. И в конце каждого сообщения ты пишешь - Ура!"
                    },
                    {
                      "role": "user",
                      "text": "Привет. Как дела?"
                    }
                  ]
                }
                """;

        ObjectMapper mapper = new ObjectMapper();
        YaChatDTO chatDTO = mapper.readValue(json, YaChatDTO.class);

        assertNotNull(chatDTO);
        assertNotNull(chatDTO.getModelUri());
        assertNotNull(chatDTO.getCompletionOptions());
        assertNotNull(chatDTO.getMessages());
    }
}
