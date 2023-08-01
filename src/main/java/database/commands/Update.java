package database.commands;

import database.Connector;
import database.Types;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class Update {
    protected static Statement statement;

    public void createEntity(String username, Types.EntityType entityType, String name, String bio, String pic_link) {
        try {
            if (check_if_username_exist(username))
                return;
            String command = String.format("Insert into entities(username,entity_type,name,bio,picture) " +
                    " values('%s','%s','%s','%s','%s')", username, entityType, name, bio, pic_link);
            statement.execute(command);
            System.out.println(username + " added to the entity table");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void delete_account(String username) {
        statement=Connector.getStatement();
        try {
            if (!check_if_username_exist(username))
                return;
            String command = String.format("DELETE FROM entities where entities.username='%s';", username);
            statement.execute(command);
             command = String.format("DELETE FROM account where account.username='%s';", username);
            statement.execute(command);
            Connector.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void change_bio(String username, String newBio) {
        statement=Connector.getStatement();
        try {
            if (!check_if_username_exist(username))
                return;
            String command = String.format("update entities set bio='%s' where entities.username='%s';", newBio, username);
            statement.execute(command);
            Connector.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    protected boolean check_if_username_exist(String username) throws SQLException {
        String command = String.format("select * from entities where entities.username='%s'", username);
        ResultSet resultSet = statement.executeQuery(command);
        boolean res = resultSet.next();
        if (res)
            System.out.printf("user %s  already exist in entities table, be careful\n", username);
        return res;
    }
}
