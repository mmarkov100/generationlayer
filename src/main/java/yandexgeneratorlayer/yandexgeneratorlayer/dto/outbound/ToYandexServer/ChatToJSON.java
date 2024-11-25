package yandexgeneratorlayer.yandexgeneratorlayer.dto.outbound.ToYandexServer;

/*
Этот класс описывает структуру JSON-запроса, который отправляется на Yandex API
 */

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import yandexgeneratorlayer.yandexgeneratorlayer.dto.internal.ChatInternalDTO;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ChatToJSON {

    // Указывает модель, используемую для генерации текста.
    @SerializedName("modelUri")
    private String modelUri;

    // Представляет параметры генерации текста.
    @SerializedName("completionOptions")
    private CompletionOptionsToJSON completionOptions;

    // Список сообщений, включённых в запрос.
    @SerializedName("messages")
    private List<MessageToJSON> messages = new ArrayList<>();

    public ChatToJSON(ChatInternalDTO chatService) {
        this.modelUri = chatService.getModelUri();
        this.completionOptions = new CompletionOptionsToJSON(chatService.getCompletionOptions());
        chatService.getMessages().forEach(message ->
                this.messages.add(new MessageToJSON(message))
        );
    }

    // преобразует объект ChatToJSON в строку JSON
    public String toJSON() {
        Gson gson = new Gson();
        return gson.toJson(this); // Преобразуем объект в JSON-строку
    }
}
