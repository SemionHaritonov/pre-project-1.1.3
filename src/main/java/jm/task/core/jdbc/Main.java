package jm.task.core.jdbc;

import jm.task.core.jdbc.model.Person;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;


public class Main {
    public static void main(String[] args) throws SQLException {
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();

        Person[] people = {new Person("First", "User", (byte) 11),
                new Person("Second", "User", (byte) 22),
                new Person("Third", "User", (byte) 33),
                new Person("Fourth", "User", (byte) 44),};

        for (Person person :
                people) {
            userService.saveUser(person.getName(), person.getLastName(), person.getAge());
            System.out.println("User with name - " + person.getName() + " added to DB");
        }

        userService.removeUserById(2);

        userService.getAllUsers().forEach(System.out::println);

        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
