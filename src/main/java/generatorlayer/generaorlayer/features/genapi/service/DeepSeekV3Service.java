package generatorlayer.generaorlayer.features.genapi.service;

import generatorlayer.generaorlayer.features.genapi.dto.outbound.ResponseGenApiDTO;
import generatorlayer.generaorlayer.features.genapi.interfaces.GenApiRequest;
import generatorlayer.generaorlayer.features.genapi.interfaces.GenApiResponse;
import generatorlayer.generaorlayer.features.genapi.interfaces.GenApiService;
import org.springframework.stereotype.Service;

@Service("deepseekv3")
public class DeepSeekV3Service implements GenApiService {

    @Override
    public ResponseGenApiDTO convertToOutbound(GenApiResponse genApiResponse) throws Exception {
        return null;
    }

    @Override
    public GenApiResponse generateResponse(GenApiRequest genApiRequest) throws Exception {
        return null;
    }
}
