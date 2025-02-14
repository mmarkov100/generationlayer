package generatorlayer.generaorlayer.features.genapi.dto.inbound;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class MessageStartDTO {

    private Long requestId;
    private String model;
    private String status;
}
