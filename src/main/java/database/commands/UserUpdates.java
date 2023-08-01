package database.commands;

import database.Connector;
import database.Types;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserUpdates extends Update {


    public void signUp(String username, Types.EntityType entityType, String name, String bio, String pic_link, String password, String phoneNumber, String lastname) {
        statement = Connector.getStatement();
        try {
            createEntity(username, entityType, name, bio, pic_link);
            if (check_if_phoneNumber(phoneNumber))
                return;
            String command = String.format("Insert into account(username,password,phoneNumber,lastname) " +
                    " values('%s','%s','%s','%s')", username, password, phoneNumber, lastname);
            statement.execute(command);
            System.out.println(username + " added to the account table");
            Connector.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean check_if_phoneNumber(String phoneNumber) throws SQLException {
        String command = String.format("select * from account where  account.phoneNumber='%s'", phoneNumber);
        ResultSet resultSet = statement.executeQuery(command);
        boolean res = resultSet.next();
        if (res)
            System.out.printf("phoneNumber: %s already exist in account table, be careful\n", phoneNumber);
        return res;
    }


    public void login(String username, String password) {
        statement = Connector.getStatement();
        try {
            if (!check_if_username_exist(username))
                return;
            String command = String.format("select * from account where account.username='%s' and account.password='%s'", username, password);
            ResultSet rs = statement.executeQuery(command);
            if (!rs.next())
                System.out.println("wrong password,try again");
            else System.out.println("user logged in");
            Connector.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void printTable() throws SQLException {
        statement = Connector.getStatement();
        String command = String.format("select * from Users");
        ResultSet rs = statement.executeQuery(command);
        while (rs.next()) {
            System.out.printf("id:%d\t|\tusername:%s\t|\tphoneNumber:%s\t|\tbio:%s\t|\tpassword:%s%n", rs.getLong("user_id"),
                    rs.getString("username"), rs.getString("phoneNumber"), rs.getString("bio"), rs.getString("password"));
        }
        Connector.close();
    }


}
