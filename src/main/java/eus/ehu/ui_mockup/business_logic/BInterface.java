package eus.ehu.ui_mockup.business_logic;

import eus.ehu.ui_mockup.domain.Project;
import eus.ehu.ui_mockup.domain.User;

import java.util.List;

public interface BInterface {
    boolean verifyUser(String username, String password);
    boolean createUser(String username, String password);
    List<Project> retrieveProjects(Long id);
    boolean createProject(Long id, Project project);
}
