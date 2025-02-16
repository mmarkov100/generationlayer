package generatorlayer.generaorlayer.features.genapi.service;

import generatorlayer.generaorlayer.features.genapi.dto.outbound.ResponseGenApiDTO;
import generatorlayer.generaorlayer.features.genapi.dto.inbound.GenApiRequest;
import generatorlayer.generaorlayer.features.genapi.dto.outbound.GenApiResponse;

public interface GenApiService<T extends GenApiRequest, R extends GenApiResponse> {
    ResponseGenApiDTO convertToOutbound(R genApiResponse) throws Exception;
    R generateResponse(T genApiRequest) throws Exception;
}
