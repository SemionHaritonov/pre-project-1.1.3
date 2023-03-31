package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.Person;

import java.util.List;

public interface UserDao {
    void createUsersTable();

    void dropUsersTable();

    void saveUser(String name, String lastName, byte age);

    void removeUserById(long id);

    List<Person> getAllUsers();

    void cleanUsersTable();
}
