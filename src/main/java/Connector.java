import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Connector {
    private static final String url = "jdbc:postgresql://localhost:5432/Messenger";
    private static final String user = "postgres";
    private static final String password = "san.mousavi";
    private static Connector connector;
    private static Connection connection;

    private Connector() {

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static Connection getStatement() {
        if (connector == null)
            connector = new Connector();
        return connection;
    }

}
