package generatorlayer.generaorlayer.features.genapi.interfaces;

public interface GenApiResponse {
    String getRole();
    String getText();
    Integer getUsageTokens();
    Integer getCompletionTokens();
    Integer getAllTokens();
    double getCost();
}
