package eus.ehu.TxikIA.business_logic;

import eus.ehu.TxikIA.domain.Project;

import java.util.List;
import java.util.UUID;

public interface BInterface {
    boolean verifyUser(String username, String password);
    void createUser(String username, String password);
    boolean firstLogin(UUID user_id);
    List<Project> retrieveProjects(UUID id);
    boolean createProject(UUID user_id, Project project);
    boolean editProject(UUID id, String name, String description);
    boolean deleteProject(UUID user_id, UUID project_id);
}
