package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;

public class Connector {
    private static final String url = "jdbc:postgresql://localhost:5432/Messenger";
    private static final String user = "postgres";
    private static final String password = "Sara5560734055";
    private static Connector connector;
    private static Connection connection;
    private static Statement statement;

    private Connector() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static Statement getStatement() {
        try {
            if (statement == null) {
                statement = getConnection().createStatement();
            }
            return statement;
        } catch (SQLException e) {
            System.out.println("could not create the statement");
            ;
        }
        return getStatement();
    }

    public static Connection getConnection() {
        if (connector == null)
            connector = new Connector();
        return connection;
    }

    public static Connector getConnector() {
        if (connector == null) connector = new Connector();
        return connector;
    }

    public PreparedStatement getPreparedStatement(String query) {
        try {
            return connection.prepareStatement(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void close() {
        try {
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("could not close connection!");
        }
    }

    public void closeConnection() {
        try {
            connection.close();
            connector = null;
        } catch (SQLException e) {
            System.out.println("could not close connection!");
        }
    }

}
