package eus.ehu.ui_mockup.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

// User is reserved in H2
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private static String username_static;
    private static Long id_static;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static Long getId_static() {
        return id_static;
    }

    public static void setId_static(Long id_static) {
        User.id_static = id_static;
    }

    public Long getId() {
        return id;
    }

    public void addProject(Project project) {
        projects.add(project);
    }
}
