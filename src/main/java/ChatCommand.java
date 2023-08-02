import Types.profileType;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ChatCommand {
    private Connector connector;

    public void createChat(String entityName, profileType type, String name,
                           String bio, String picture) {

    }

    public void addParticipants(int entityID1, int entityID2) throws SQLException {
        connector = Connector.getConnector();
        String checkQuery = "select * from participants where entity1_id = ? and entity2_id = ?";
        PreparedStatement preparedStatement = connector.getPreparedStatement(checkQuery);
        try {
            preparedStatement.setInt(1, entityID1);
            preparedStatement.setInt(2, entityID2);
        } catch (SQLException e) {
            System.out.println("could not set data in preparedStatement/ add participants");
        }
        if (preparedStatement.executeQuery().next()) {
            System.out.println("data is duplicate");
            return;
        }
        String query = "insert into participants(entity1_id, entity2_id)" +
                " values(?, ?)";
        preparedStatement = connector.getPreparedStatement(query);
        try {
            preparedStatement.setInt(1, entityID1);
            preparedStatement.setInt(2, entityID2);
        } catch (SQLException e) {
            System.out.println("could not set data in preparedStatement/ add participants");
        }
        if (preparedStatement.executeUpdate() > 0) {
            System.out.println("new participant is added.");
        }
        else System.out.println("could not add this participant.");
        connector.closeConnection();
    }

    public void addLastSeenMessage(int entityID1, int entityID2, int messageID) throws SQLException {
        connector = Connector.getConnector();
        String query = "update participants set last_message_seen = ? " +
                "where entity1_id = ? and entity2_id = ?";
        PreparedStatement preparedStatement = connector.getPreparedStatement(query);
        try {
            preparedStatement.setInt(1, messageID);
            preparedStatement.setInt(2, entityID1);
            preparedStatement.setInt(3, entityID2);
        } catch (SQLException e) {
            System.out.println("could not set data in preparedStatement/ add last seen message");
        }
        if (preparedStatement.executeUpdate() > 0)
            System.out.println("last seen message is added");
        else System.out.println("could not set last seen message!");
        connector.closeConnection();
    }

}
