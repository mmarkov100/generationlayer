package generatorlayer.generaorlayer.features.genapi.interfaces;

import generatorlayer.generaorlayer.features.genapi.dto.outbound.ResponseGenApiDTO;

public interface GenApiService<T extends GenApiRequest, R extends GenApiResponse> {
    ResponseGenApiDTO convertToOutbound(R genApiResponse) throws Exception;
    R generateResponse(T genApiRequest) throws Exception;
}
