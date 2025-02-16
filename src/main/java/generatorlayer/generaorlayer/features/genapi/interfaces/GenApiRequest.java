package generatorlayer.generaorlayer.features.genapi.interfaces;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import generatorlayer.generaorlayer.features.genapi.dto.inbound.ChatGPT4oMiniRequestImpl;
import generatorlayer.generaorlayer.features.genapi.dto.inbound.DeepSeekV3RequestImpl;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "model"  // Укажите поле, которое будет определять тип модели
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ChatGPT4oMiniRequestImpl.class, name = "chatgpt4o-mini"),
        @JsonSubTypes.Type(value = DeepSeekV3RequestImpl.class, name = "deepseek-v3")
})
public interface GenApiRequest {
}
