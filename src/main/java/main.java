import database.commands.UserCommand;

import java.sql.*;

public class main {
    public static void main(String[] args) throws SQLException {
        UserCommand userCommand = new UserCommand();
        userCommand.signUp("aouldcott0", "06416856140", "Wanstall", "Ouldcott", "zC2@3q~Av", "Accountant I");

    }


}
