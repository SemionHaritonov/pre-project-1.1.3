package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;


import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getPostgresConfiguration;

public class UserDaoHibernateImpl implements UserDao {

    //private final SessionFactory sessionFactory;

    public UserDaoHibernateImpl() {
        //Configuration configuration = Util.getPostgresConfiguration();
        //sessionFactory = createSessionFactory(configuration);
    }


    @Override
    public void createUsersTable() {
        try {
            SessionFactory sessionFactory = createSessionFactory(Util.getPostgresConfiguration());
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.createSQLQuery("create table if not exists users " +
                    "(" +
                    "id bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY, " +
                    "name varchar(256), " +
                    "lastName varchar(256), " +
                    "age smallint" +
                    ")").executeUpdate();
            session.getTransaction().commit();
            session.close();
            sessionFactory.close();
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void dropUsersTable() {
        try {
            SessionFactory sessionFactory = createSessionFactory(Util.getPostgresConfiguration());
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery("drop table if exists users").executeUpdate();
            transaction.commit();
            session.close();
            sessionFactory.close();
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            SessionFactory sessionFactory = createSessionFactory(Util.getPostgresConfiguration());
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
            session.close();
            sessionFactory.close();
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void removeUserById(long id) {
        try {
            SessionFactory sessionFactory = createSessionFactory(Util.getPostgresConfiguration());
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            Object persistentInstance = session.load(User.class, id);
            session.delete(persistentInstance);

            transaction.commit();
            session.close();
            sessionFactory.close();
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            SessionFactory sessionFactory = createSessionFactory(Util.getPostgresConfiguration());
            Session session = sessionFactory.openSession();
            users = (List<User>) session.createCriteria(User.class, "from users").list();
            session.close();
            sessionFactory.close();
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
        return  users;
    }

    @Override
    public void cleanUsersTable() {
        try {
            SessionFactory sessionFactory = createSessionFactory(Util.getPostgresConfiguration());
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery("delete from users").executeUpdate();
            transaction.commit();
            session.close();
            sessionFactory.close();
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }
}
