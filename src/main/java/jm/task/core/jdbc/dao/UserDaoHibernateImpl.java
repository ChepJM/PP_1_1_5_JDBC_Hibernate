package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.sql.SQLException;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        String query = "CREATE TABLE IF NOT EXISTS User (" +
                "    id INT NOT NULL AUTO_INCREMENT," +
                "    name VARCHAR(50)," +
                "    lastName VARCHAR(50)," +
                "    age INT," +
                "    PRIMARY KEY (id)" +
                ")";
        Session session = Util.getSessionFactory().openSession();
        session.beginTransaction();
        session.createSQLQuery(query).executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void dropUsersTable() {
        String query = "DROP TABLE IF EXISTS User";
        Session session = Util.getSessionFactory().openSession();
        session.beginTransaction();
        session.createSQLQuery(query).executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.getSessionFactory().openSession();
        session.beginTransaction();

        User user = new User(name, lastName, age);
        session.save(user);

        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getSessionFactory().openSession();
        session.beginTransaction();

        User user = session.get(User.class, id);
        session.delete(user);

        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = null;
        Session session = Util.getSessionFactory().openSession();
        session.beginTransaction();

        users = session.createQuery("FROM User").getResultList();

        session.getTransaction().commit();
        session.close();
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        session.beginTransaction();

        session.createQuery("DELETE User").executeUpdate();

        session.getTransaction().commit();
        session.close();
    }
}
