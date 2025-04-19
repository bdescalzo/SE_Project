package eus.ehu.TxikIA.domain;

public class SystemPrompts {
    String normalization_prompt;
    String code_generation_prompt;
    String explanation_prompt;

    public String get(String promptName) {
        switch (promptName) {
            case "normalization_prompt":
                return normalization_prompt;
            case "code_generation_prompt":
                return code_generation_prompt;
            case "explanation_prompt":
                return explanation_prompt;
            default:
                return null;
        }
    }
}
