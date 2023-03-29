package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;


public class Util {
    private static final String hibernate_show_sql = "false";
    private static final String hibernate_hbm2ddl_auto = "update";
    private static final String user = "test";
    private static final String password = "test";

    public static Connection getPostgresConnection() {
        try {
            DriverManager.registerDriver((Driver) Class.forName("com.mysql.jdbc.Driver").
                    getDeclaredConstructor().newInstance());
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

    public static Configuration getPostgresConfiguration() {
        Configuration configuration = new org.hibernate.cfg.Configuration();
        configuration.addAnnotatedClass(User.class);
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:postgresql://" +
                "localhost:" +
                "5432/" +
                "test_db");
        configuration.setProperty("hibernate.connection.username", user);
        configuration.setProperty("hibernate.connection.password", password);
        configuration.setProperty("hibernate.show_sql", hibernate_show_sql);
        configuration.setProperty("hibernate.hbm2ddl.auto", hibernate_hbm2ddl_auto);
        return configuration;
    }
}

