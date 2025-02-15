package generatorlayer.generaorlayer.features.genapi.dto.inbound;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class MessageStartDTO {

    @JsonProperty("request_id")
    private Long requestId;
    private String model;
    private String status;
}
