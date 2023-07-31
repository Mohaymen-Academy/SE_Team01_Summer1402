package database.commands;

import database.Connector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MessageCommand {

    public static enum MessageType {TEXT, IMAGE, VIDEO, SOUND, FILE};
    private Statement statement;

    public MessageCommand() {
        try {
            statement =  Connector.getConnection().createStatement();
        } catch (SQLException e) {
            System.out.println("could not create statement!");
        }
    }

    public String sendMessage(int chat_id, int sender,
                              String messageContext, MessageType messageType) throws SQLException {
        String query = String.format("insert into messages(chat_id, sender, message_context, message_type)" +
                " values('%s', '%s', '%s', '%s')", chat_id, sender, messageContext, messageType);
        if (statement.executeUpdate(query) > 0)
            return "Message is sent.";
        else return "Could not send message!";
    }

    public String editMessage(int messageID, String newMessage) throws SQLException {
        String query = String.format("update messages" +
                " set message_context = '%s'" +
                " where messages.message_id = '%s'",newMessage, messageID);
        if (statement.executeUpdate(query) > 0)
            return "Message is edited.";
        else return "Could not edit message!";
    }

    public String deleteMessage(int messageID) throws SQLException {
        String query = String.format("delete from Messages" +
                " where messages.message_id = '%s'", messageID);
        if (statement.executeUpdate(query) > 0)
            return "Message is deleted.";
        else return "Could not delete message!";
    }

    public void getAllMessages(int userID) throws SQLException {
        String query = String.format("select message_context, message_type from messages\n" +
                " where messages.sender = '%S'", userID);
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            String messageContext = resultSet.getString("message_context");
            MessageType type = MessageType.valueOf(resultSet.getString("message_type"));
            System.out.println("message: " + messageContext + " | type: " + type);
        }
    }

    public int getAllMessagesNum(int userID) throws SQLException {
        String query = String.format("select count(*) from Messages" +
                " where sender = '%s'", userID);
        ResultSet resultSet = statement.executeQuery(query);
        if (resultSet.next()) return resultSet.getInt("count");
        else return 0;
    }


    public int getRelationNum(int userID) throws SQLException {
        String query = String.format("select distinct count(*) from participants where chat_id in" +
                "(select chat_id from participants where user_id = '%s')" +
                " and user_id != '%s'", userID, userID);
        ResultSet resultSet = statement.executeQuery(query);
        if (resultSet.next()) return resultSet.getInt("count");
        else return 0;
    }

    public double getAVGMessages(int userID) throws SQLException {
        String query = "select count(distinct sender) from messages)";
        ResultSet resultSet = statement.executeQuery(query);
        if (resultSet.next()) return
                (double) getAllMessagesNum(userID) / resultSet.getInt("count");
        else return 0;
    }

}
