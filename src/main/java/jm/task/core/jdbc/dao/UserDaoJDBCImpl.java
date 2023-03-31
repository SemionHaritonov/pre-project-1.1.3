package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.Person;
import jm.task.core.jdbc.util.Executor;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection;
    private Executor executor;

    public UserDaoJDBCImpl() {
        connection = Util.getPostgresConnection();
        executor = new Executor(connection);
    }

    public void createUsersTable() {
        try {
            executor.execUpdate("create table if not exists users " +
                    "(" +
                    "id bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY, " +
                    "name varchar(256), " +
                    "lastName varchar(256), " +
                    "age smallint" +
                    ")");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try {
            executor.execUpdate("drop table if exists users");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            executor.execUpdate("insert into users (name, lastName, age) " +
                    "values ('" + name + "', '" + lastName + "', '" + age + "')");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try {
            executor.execUpdate("delete from users where id='" + id + "'");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Person> getAllUsers() {
        try {
            return executor.execQuery("select * from users ", result -> {
                List<Person> resultPeople = new ArrayList<>();
                while (result.next()) {
                    Person person = new Person(result.getString("name"),
                            result.getString("lastName"),
                            result.getByte("age"));
                    person.setId(result.getLong("id"));
                    resultPeople.add(person);
                }
                return resultPeople;
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cleanUsersTable() {
        try {
            executor.execUpdate("delete from users");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
