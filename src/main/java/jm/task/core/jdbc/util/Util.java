package jm.task.core.jdbc.util;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;


public class Util {

    public static Connection getPostgresConnection() {
        try {
            DriverManager.registerDriver((Driver) Class.forName("com.mysql.jdbc.Driver").
                    getDeclaredConstructor().newInstance());
            String user = "test";
            String password = "test";
            StringBuilder url = new StringBuilder();
            url.
                    append("jdbc:postgresql://").
                    append("localhost:").
                    append("5432/").
                    append("test_db?").
                    append("user=").
                    append(user).
                    append("&password=").
                    append(password);
            System.out.println("URL: " + url + "\n");
            return DriverManager.getConnection(url.toString());
        } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}

