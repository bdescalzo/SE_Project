package eus.ehu.TxikIA.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

// User is reserved in H2
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private static String username_static;
    private static UUID id_static;

    private String username;
    private String password;

    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;

    @OneToMany (mappedBy = "owner")
    private Set<Project> projects = new HashSet<>();

    public User() {

    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.createdAt = LocalDateTime.now();
        this.lastLogin = LocalDateTime.now();
        username_static = username;
        id_static = id;
    }

    public Set<Project> getProjects() {
        return projects;
    }


    public static String getUsername_static() {
        return username_static;
    }

    public static void set_username_static(String username) {
        username_static = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public static UUID getId_static() {
        return id_static;
    }

    public static void setId_static(UUID id_static) {
        User.id_static = id_static;
    }

    public UUID getId() {
        return id;
    }

    public void addProject(Project project) {
        projects.add(project);
    }

    public void removeProject(Project project) {
        projects.remove(project);
    }
}
