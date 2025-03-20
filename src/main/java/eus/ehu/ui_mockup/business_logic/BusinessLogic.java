package eus.ehu.ui_mockup.business_logic;

import eus.ehu.ui_mockup.data_access.DBController;
import eus.ehu.ui_mockup.domain.Project;
import eus.ehu.ui_mockup.domain.User;

import java.util.List;

public class BusinessLogic implements BInterface{

    DBController db = new DBController();

    @Override
    public boolean verifyUser(String username, String password) {
        return db.verifyUser(username, password);
    }

    @Override
    public boolean createUser(String username, String password) {
        return db.createUser(username, password);
    }

    @Override
    public List<Project> retrieveProjects(Long id) {
        return db.getProjects(id);
    }

    @Override
    public boolean createProject(Long id, Project project) {
        return db.storeProject(id, project);
    }


}
