package database.commands;

import database.Connector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserCommand {
    private static Connection con;
    private static Statement statement;

    public UserCommand() {
        con = Connector.getStatement();
        try {
            statement = con.createStatement();
        } catch (SQLException e) {
            System.out.println("could not create the connection");
            ;
        }
    }

    public void signUp(String username, String phoneNumber, String name, String lastname, String password, String bio) {
        try {
            if (check_if_signedUp(username, phoneNumber))
                return;
            String command = String.format("Insert into Users(phoneNumber,username,name,lastname,password,bio) " +
                    " values('%s','%s','%s','%s','%s','%s')", phoneNumber, username, name, lastname, password, bio);
            statement.execute(command);
            System.out.println(username+" added to the database");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean check_if_signedUp(String username, String phoneNumber) throws SQLException {
        String command = String.format("select * from Users where Users.username='%s' or Users.phoneNumber='%s'", username, phoneNumber);
        ResultSet resultSet = statement.executeQuery(command);
        boolean res = resultSet.next();
        if (res)
            System.out.printf("user %s with phoneNumber: %s already exist, be careful\n", username, phoneNumber);
        return res;
    }


    public void login(String username, String password) {
        try {
            if (!check_if_has_account(username))
                return;
            String command = String.format("select * from Users where Users.username='%s' and Users.password='%s'", username, password);
            ResultSet rs = statement.executeQuery(command);
            if (!rs.next())
                System.out.println("wrong password,try again");
            else System.out.println("user logged in");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean check_if_has_account(String username) throws SQLException {
        ResultSet resultSet;
        String command = String.format("select * from Users where Users.username='%s'", username);
        resultSet = statement.executeQuery(command);
        boolean res = resultSet.next();
        if (!res)
            System.out.printf("%s has no account, be careful\n", username);
        return res;
    }

    public void delete_account(String username) {
        try {
            if (!check_if_has_account(username))
                return;
            String command = String.format("DELETE FROM Users where Users.username='%s';", username);
            statement.execute(command);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void change_bio(String username, String newBio) {
        try {
            if (!check_if_has_account(username))
                return;
            String command = String.format("update Users set bio='%s' where Users.username='%s';", newBio, username);
            statement.execute(command);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void printTable() throws SQLException {
        String command = String.format("select * from Users");
        ResultSet rs = statement.executeQuery(command);
        while (rs.next()) {
            System.out.printf("id:%d\t|\tusername:%s\t|\tphoneNumber:%s\t|\tbio:%s\t|\tpassword:%s%n", rs.getLong("user_id"),
                    rs.getString("username"), rs.getString("phoneNumber"), rs.getString("bio"), rs.getString("password"));
        }
    }


}
