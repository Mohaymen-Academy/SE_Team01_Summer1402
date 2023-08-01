package database.commands;

import database.Connector;
import database.Types;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MessageCommand {
    private Connector connector;

    public void sendMessage(int entityID, int senderID,
                            String messageText, String bytes,
                            String fileExtension) throws SQLException {
        connector = Connector.getConnector();
        String query = "insert into messages(entity_id, sender_id, message_text, message_byte, file_extension)" +
                " values(?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connector.getPreparedStatement(query);
        try {
            preparedStatement.setInt(1, entityID);
            preparedStatement.setInt(2, senderID);
            preparedStatement.setString(3, messageText);
            preparedStatement.setString(4, bytes);
            preparedStatement.setString(5, fileExtension);
        } catch (SQLException e) {
            System.out.println("could not set data in preparedStatement/ send message");
        }
        System.out.println(preparedStatement.toString());
        if (preparedStatement.executeUpdate() > 0)
            System.out.println("Message is sent");
        else System.out.println("could not send message!");
        connector.closeConnection();
    }

    public void editMessage(int messageID, String newText) throws SQLException {
        connector = Connector.getConnector();
        String query = "update messages set message_text = ? where messages.message_id = ? ";
        PreparedStatement preparedStatement = connector.getPreparedStatement(query);
        try {
            preparedStatement.setString(1, newText);
            preparedStatement.setInt(2, messageID);
        } catch (SQLException e) {
            System.out.println("could not set data in preparedStatement/ edit message");
        }
        if (preparedStatement.executeUpdate() > 0)
            System.out.println("Message is edited");
        else System.out.println("could not edit message!");
        connector.closeConnection();
    }

    public void deleteMessage(int messageID) throws SQLException {
        connector = Connector.getConnector();
        String query = "delete from Messages where messages.message_id = ?";
        PreparedStatement preparedStatement = connector.getPreparedStatement(query);
        try {
            preparedStatement.setInt(1, messageID);
        } catch (SQLException e) {
            System.out.println("could not set data in preparedStatement/ delete message");
        }
        if (preparedStatement.executeUpdate() > 0)
            System.out.println("Message is deleted");
        else System.out.println("could not delete message!");
        connector.closeConnection();
    }

    public void getAllMessages(int userID) throws SQLException {
        connector = Connector.getConnector();
        String query = "select message_text, message_byte, file_extension from messages" +
                " where messages.sender_id = ?";
        PreparedStatement preparedStatement = connector.getPreparedStatement(query);
        try {
            preparedStatement.setInt(1, userID);
        } catch (SQLException e) {
            System.out.println("could not set data in preparedStatement/ get all messages");
        }
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String messageContext = resultSet.getString("message_context");
            String bytes = resultSet.getString("message_byte");
            String fileExtension = resultSet.getString("file_extension");
            System.out.println("message text: " + messageContext);
        }
        connector.closeConnection();
    }

    public int getAllMessagesNum(int userID) throws SQLException {
        connector = Connector.getConnector();
        String query = "select count(*) from Messages where sender_id = ?";
        PreparedStatement preparedStatement = connector.getPreparedStatement(query);
        try {
            preparedStatement.setInt(1, userID);
        } catch (SQLException e) {
            System.out.println("could not set data in preparedStatement/ get all messages num");
        }
        ResultSet resultSet = preparedStatement.executeQuery();
        connector.closeConnection();
        if (resultSet.next())
            return resultSet.getInt("count");
        else return 0;
    }


    public int getRelationsNum(int userID) throws SQLException {
        String query = "select distinct count(*) from participants where entity1_id in" +
                "(select entity2_id from participants where entity1_id = ?" +
                " and entity1_id != ?";
        PreparedStatement preparedStatement = connector.getPreparedStatement(query);
        try {
            preparedStatement.setInt(1, userID);
            preparedStatement.setInt(2, userID);
        } catch (SQLException e) {
            System.out.println("could not set data in preparedStatement/ get relations num");
        }
        ResultSet resultSet = preparedStatement.executeQuery();
        connector.closeConnection();
        if (resultSet.next())
            return resultSet.getInt("count");
        else return 0;
    }

    public double getAVGMessages(int userID) throws SQLException {
        connector = Connector.getConnector();
        String query = "select count(distinct sender_id) from messages)";
        ResultSet resultSet = connector.getStatement().executeQuery(query);
        if (resultSet.next()) return
                (double) getAllMessagesNum(userID) / resultSet.getInt("count");
        else return 0;
    }

}
