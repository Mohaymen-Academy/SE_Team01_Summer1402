import database.commands.MessageCommand;
import database.commands.UserCommand;

import java.sql.*;

public class main {
    public static void main(String[] args) throws SQLException {
//        UserCommand userCommand = new UserCommand();
//        userCommand.signUp("aouldcott0", "06416856140", "Wanstall", "Ouldcott", "zC2@3q~Av", "Accountant I");

       // MessageCommand messageCommand = new MessageCommand();
       // messageCommand.sendMessage(2,1,"salam", MessageCommand.MessageType.TEXT);
//        messageCommand.sendMessage(3,1,"salam,khubi?", MessageCommand.MessageType.TEXT);
//        messageCommand.sendMessage(1,4,"pic1", MessageCommand.MessageType.IMAGE);
        //messageCommand.editMessage(1,"hello");
        //messageCommand.sendMessage(2,1,"ch khbr", MessageCommand.MessageType.TEXT);
        //messageCommand.deleteMessage(4);

        UserCommand userCommand = new UserCommand();
        //userCommand.signUp("a", "12345678901", "a", "b", "1234", "a");
        userCommand.login("a", "1234hggjgh5");
    }


}
