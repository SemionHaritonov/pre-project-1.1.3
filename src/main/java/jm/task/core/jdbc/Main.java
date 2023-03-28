package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import java.sql.SQLException;


public class Main {
    public static void main(String[] args) throws SQLException {
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();

        User[] users = {new User("First", "User", (byte) 11),
                new User("Second", "User", (byte) 22),
                new User("Third", "User", (byte) 33),
                new User("Fourth", "User", (byte) 44),};

        for (User user:
             users) {
            userService.saveUser(user.getName(), user.getLastName(), user.getAge());
            System.out.println("User with name - " + user.getName() + " added to DB");
        }

        userService.getAllUsers().forEach(System.out::println);

        userService.cleanUsersTable();
        userService.dropUsersTable();



    }
}
