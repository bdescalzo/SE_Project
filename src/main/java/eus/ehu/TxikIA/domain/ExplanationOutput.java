package eus.ehu.TxikIA.domain;

public class ExplanationOutput {
    private String full_answer;
    private String answer_context;

    public ExplanationOutput() {

    }

    public String get_full_answer() {
        return full_answer;
    }

    public String get_answer_context() {
        return answer_context;
    }
}
