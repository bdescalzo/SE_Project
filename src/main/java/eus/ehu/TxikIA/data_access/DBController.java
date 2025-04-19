package eus.ehu.TxikIA.data_access;

import eus.ehu.TxikIA.domain.ChatSession;
import eus.ehu.TxikIA.domain.Message;
import eus.ehu.TxikIA.domain.Project;
import eus.ehu.TxikIA.domain.User;
import jakarta.persistence.*;
import org.apache.logging.log4j.LogManager;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class DBController {

    protected EntityManager db;
    protected EntityManagerFactory emf;

    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(DBController.class);

    public DBController() {

        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();

        log.info("Creating connection to local H2 Database......");

        try {
            emf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            db = emf.createEntityManager();
            log.info("\033[0;32mSuccesfully connected to local H2 Database \033[0m");
        } catch (Exception e) {
            log.error("Cannot establish a connection to local H2 Database!!!");
            e.printStackTrace();
            StandardServiceRegistryBuilder.destroy(registry);

        }



    }

    public boolean verifyUser(String user, String password) {
        TypedQuery<User> query = db.createQuery("select u from User u where u.username =:user and u.password = :password ", User.class);
        query.setParameter("user", user);
        query.setParameter("password", password);
        log.info("Verifying user "+user+"..........");
        try {
            User db_user = query.getSingleResult();
            User.setId_static(db_user.getId());
            User.set_username_static(user);
            db.getTransaction().begin();
            db_user.setLastLogin(LocalDateTime.now());
            db.persist(db_user);
            db.getTransaction().commit();
            return true;
        } catch (NoResultException e) {
            log.error("Can't log in as User "+user);
            return false;
        }

    }

    public void createUser(String user, String password) {
        log.warn("Creating user "+user+"...");
        User dbUser = new User(user, password);
        try {
            db.getTransaction().begin();
            dbUser.setLastLogin(dbUser.getCreatedAt());
            db.persist(dbUser);
            db.getTransaction().commit();
            User.setId_static(dbUser.getId());
            log.info("\033[0;32mUser " +User.getUsername_static()+" has been created! \033[0m");
        }
        catch (Exception e) {
            db.getTransaction().rollback();
        }
    }

    public boolean firstLogin(UUID user_id){
        User user = db.find(User.class, user_id);
        return user.getLastLogin()==user.getCreatedAt();
    }

    public List<Project> getProjects(UUID userId) {
        User db_found = db.find(User.class, userId);
        log.info("Retrieving projects for user "+db_found.getUsername()+"......");
        TypedQuery<Project> query = db.createQuery("select p from Project p  where p.owner = :owner", Project.class);
        query.setParameter("owner", db_found);
        List<Project> projects = query.getResultList();
        if (!projects.isEmpty()) {
            log.info("\033[0;32mFound "+projects.size()+" projects for User "+db_found.getUsername()+"\033[0m");
        } else {
            log.warn("No projects found for user "+db_found.getUsername());
        }
        return query.getResultList();
    }

    public boolean storeProject(UUID id, Project project) {
        User db_user = db.find(User.class, id);
        log.info("Storing new project for User "+db_user.getUsername()+"......");
        db.getTransaction().begin();
        db_user.addProject(project);
        project.setOwner(db_user);
        db.merge(project);
        db.getTransaction().commit();
        log.info("\033[0;32mNew Project has been succesfully created!!!\033[0m");
        return true;
    }

    public boolean editProject(UUID id, String name, String description) {
        Project db_found = db.find(Project.class, Project.getCurrent_UUID());
        log.info("Trying to edit project with UUID "+db_found.getUUID());
        db.getTransaction().begin();
        db_found.setName(name);
        db_found.setDescription(description);
        db_found.setUpdatedAt(LocalDateTime.now());
        db.merge(db_found);
        db.getTransaction().commit();
        log.info("\033[0;32mProject edited with UUID "+db_found.getUUID()+" edited successfully\033[0m");
        return true;
    }

    public boolean deleteProject(UUID user_id, UUID project_id) {
        User db_user = db.find(User.class, user_id);
        Project db_project = db.find(Project.class, project_id);
        log.info("Trying to delete project with UUID "+db_project.getUUID());
        db.getTransaction().begin();
        db_user.removeProject(db_project);
        db.remove(db_project);
        db.getTransaction().commit();
        log.warn("Project with UUID "+db_project.getUUID()+" has been deleted");
        System.out.println();
        return true;
    }


    public void addMessage(Message message) {
        // Get the project and its corresponding chat
        Project db_project = db.find(Project.class, Project.getCurrent_UUID());
        ChatSession chat = db_project.getChat();

        // Assign the chat to the message
        message.setChatSession(chat);

        // Assign the message to the chat
        db.getTransaction().begin();
        chat.addMessage(message);
        db.persist(message);
        db.getTransaction().commit();
        log.info("Message stored in project {}", Project.getCurrent_UUID());
    }

    public Project find(Class<Project> projectClass, UUID currentUuid) {
        Project db_project = db.find(Project.class, currentUuid);
        if (db_project == null) {
            log.error("Project with UUID "+currentUuid+" not found");
            return null;
        }
        return db_project;
    }
}
