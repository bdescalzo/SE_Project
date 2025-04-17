package eus.ehu.TxikIA.domain;

public class SystemPrompts {
    String normalization_prompt;
    String code_generation_prompt;
    String code_explanation_prompt;

    public String get(String promptName) {
        switch (promptName) {
            case "normalization_prompt":
                return normalization_prompt;
            case "code_generation_prompt":
                return code_generation_prompt;
            case "code_explanation_prompt":
                return code_explanation_prompt;
            default:
                return null;
        }
    }
}
