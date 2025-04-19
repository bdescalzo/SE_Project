package eus.ehu.TxikIA.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Project {
    @Id
    @GeneratedValue (strategy = GenerationType.UUID)

    private UUID id;
    private static UUID current_id;

    private String name;
    private String description;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private String subject;

    @ManyToOne
    private User owner;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "chat_session_id", referencedColumnName = "id")
    private ChatSession chat;

    public Project() {
        chat = new ChatSession();
    }

    public ChatSession getChat() {
        return chat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public static UUID getCurrent_UUID() {
        return current_id;
    }

    public static void setCurrent_UUID(UUID current_id) {
        Project.current_id = current_id;
    }

    public UUID getUUID() {
        return id;
    }
}
