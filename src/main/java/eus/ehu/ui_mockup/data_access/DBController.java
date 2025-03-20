package eus.ehu.ui_mockup.data_access;

import eus.ehu.ui_mockup.domain.Project;
import eus.ehu.ui_mockup.domain.User;
import jakarta.persistence.*;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBController {

    protected EntityManager db;
    protected EntityManagerFactory emf;


    public DBController() {


        // StandardServiceRegistryBuilder connection_params = new StandardServiceRegistryBuilder().configure();

        // connection_params.applySetting("hibernate.connection.username",user);
        // connection_params.applySetting("hibernate.connection.password",password);

        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();

        try {
            emf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
        }

        db = emf.createEntityManager();
        System.out.println("* Hacker Voice * \n    I'm in");

    }


    public boolean verifyUser(String user, String password) {
        TypedQuery<User> query = db.createQuery("select u from User u where u.username =:user and u.password = :password ", User.class);
        query.setParameter("user", user);
        query.setParameter("password", password);
        try {
            User db_user = query.getSingleResult();
            User.setId_static(db_user.getId());
            User.set_username_static(user);
            return true;
        } catch (NoResultException e) {
            return false;
        }

    }

    public boolean createUser(String user, String password) {
        boolean created = false;
        User dbUser = new User(user, password);
        db.getTransaction().begin();
        db.persist(dbUser);
        db.getTransaction().commit();
        User.setId_static(dbUser.getId());
        System.out.println("User "+user+" has been created");
        return created;
    }

    public List<Project> getProjects(Long userId) {
        User db_found = db.find(User.class, userId);
        TypedQuery<Project> query = db.createQuery("select p from Project p  where p.owner = :owner", Project.class);
        query.setParameter("owner", db_found);
        return query.getResultList();
    }

}
