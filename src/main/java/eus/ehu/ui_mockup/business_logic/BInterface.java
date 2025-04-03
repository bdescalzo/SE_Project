package eus.ehu.ui_mockup.business_logic;

import eus.ehu.ui_mockup.domain.Project;
import eus.ehu.ui_mockup.domain.User;

import java.util.List;
import java.util.UUID;

public interface BInterface {
    boolean verifyUser(String username, String password);
    boolean createUser(String username, String password);
    List<Project> retrieveProjects(UUID id);
    boolean createProject(UUID user_id, Project project);
    boolean editProject(UUID id, String name, String description);
    boolean deleteProject(UUID user_id, UUID project_id);
}
