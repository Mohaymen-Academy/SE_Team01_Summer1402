import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class UserCommand {
    private Connection con;
    private Statement statement;

    public UserCommand() throws SQLException {
        con = Connector.getStatement();
        statement = con.createStatement();
    }

    public void signUp(String username, String phoneNumber, String name, String lastname, String password, String bio) throws SQLException {
        String command = String.format("Insert into Users(phoneNumber,username,name,lastname,password,bio)  values('%s','%s','%s','%s','%s','%s')", phoneNumber, username, name, lastname, password, bio);
        System.out.println(command);
        statement.execute(command);
    }

    public void login() {

    }

    public void delete_account() {

    }

    public void change_bio() {

    }


}
