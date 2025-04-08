package eus.ehu.TxikIA.business_logic;

import eus.ehu.TxikIA.data_access.DBController;
import eus.ehu.TxikIA.domain.Project;

import java.util.List;
import java.util.UUID;

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
    public List<Project> retrieveProjects(UUID id) {
        return db.getProjects(id);
    }

    @Override
    public boolean createProject(UUID user_id, Project project) {
        return db.storeProject(user_id, project);
    }

    public boolean editProject(UUID id, String name, String description){
        return db.editProject(id, name, description);
    }

    public boolean deleteProject(UUID user_id, UUID project_id) {
        return db.deleteProject(user_id, project_id);
    }


}
