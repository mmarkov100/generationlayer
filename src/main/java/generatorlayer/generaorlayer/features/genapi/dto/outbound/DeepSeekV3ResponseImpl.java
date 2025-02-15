package generatorlayer.generaorlayer.features.genapi.dto.outbound;

import generatorlayer.generaorlayer.features.genapi.interfaces.GenApiResponse;

public class DeepSeekV3ResponseImpl implements GenApiResponse {
    @Override
    public String getRole() {
        return "";
    }

    @Override
    public String getText() {
        return "";
    }

    @Override
    public Integer getUsageTokens() {
        return 0;
    }

    @Override
    public Integer getCompletionTokens() {
        return 0;
    }

    @Override
    public Integer getAllTokens() {
        return 0;
    }

    @Override
    public double getCost() {
        return 0;
    }
}
