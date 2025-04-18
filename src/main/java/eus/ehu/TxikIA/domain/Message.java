package eus.ehu.TxikIA.domain;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Lob
    private String content;

    @Lob
    private String summary;
    private boolean isUserMessage;

    @ManyToOne
    @JoinColumn(name = "chat_session_id", nullable=false)
    private ChatSession chatSession;

    public Message(String userMessage, String summary, boolean isUserMessage) {
        this.content = userMessage;
        this.isUserMessage = isUserMessage;
        this.summary = summary;
    }

    public Message() {

    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public boolean isUserMessage() {
        return isUserMessage;
    }

    public void setUserMessage(boolean userMessage) {
        isUserMessage = userMessage;
    }

    public void setChatSession(ChatSession chat) {
        this.chatSession = chat;
    }
}
