package generatorlayer.generaorlayer.features.genapi.dto.outbound;

public interface GenApiResponse {
    String getRole();
    String getText();
    Integer getUsageTokens();
    Integer getCompletionTokens();
    Integer getAllTokens();
    double getCost();
}
