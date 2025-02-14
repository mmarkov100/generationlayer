package generatorlayer.generaorlayer.features.genapi.model;

import generatorlayer.generaorlayer.features.genapi.dto.outbound.ResponseDTO;

public interface GenApiService<T extends GenApiRequest, R extends GenApiResponse> {
    ResponseDTO convertToOutbound();
    R generateResponse(T genApiRequest) throws Exception;
}
