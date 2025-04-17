package eus.ehu.TxikIA.domain;

public class NormalizedRequest {
    private String question_type;
    private String code_needed;
    private String formalized_request;
    private String attached_data;

    // all getters
    public String getQuestion_type() {
        return question_type;
    }

    public String getCode_needed() {
        return code_needed;
    }

    public String getFormalized_request() {
        return formalized_request;
    }

    public String getAttached_data() {
        return attached_data;
    }

    public NormalizedRequest() {
    }

    public String toString() {
        return "NormalizationLLMRequest{" +
                "question_type='" + question_type + '\'' +
                ", code_needed='" + code_needed + '\'' +
                ", formalized_request='" + formalized_request + '\'' +
                ", attached_data='" + attached_data + '\'' +
                '}';
    }
}