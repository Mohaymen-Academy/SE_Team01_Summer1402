package database.commands;

import database.Connector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ChatCommand {
    public static enum ChatType {PRIVATE_CHAT, GROUP, CHANNEL}
    private Statement statement;

    public ChatCommand() {
        try {
            statement =  Connector.getConnection().createStatement();
        } catch (SQLException e) {
            System.out.println("could not create statement!");
        }
    }

    public String createChat(String link, ChatType chatType) throws SQLException {
        String query = String.format("insert into chat(chat_link, chat_type)" +
                " values('%s', '%s')", link, chatType);
        String checkQuery = String.format("select chat_id from Chat where chat_link = '%s'", link);
        ResultSet resultSet = statement.executeQuery(checkQuery);
        if (resultSet != null && resultSet.next())
            return "duplicate link!";
        if (statement.executeUpdate(query) > 0)
                return "chat created!";
        else return "could not create chat!";
    }

}
