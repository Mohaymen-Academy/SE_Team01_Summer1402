package database.commands;

import database.Connector;
import database.Types;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class EntityCommand {

    private Statement statement;
    private Connection connection;

    public EntityCommand() {
        try {
            connection = Connector.getConnection();
            statement =  connection.createStatement();
        } catch (SQLException e) {
            System.out.println("could not create statement!");
        }
    }

    public String addEntity(String entityName, Types.EntityType type, String name,
                            String bio, String picture) {
        String query = "";
        return null;
    }
}
