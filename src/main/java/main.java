import database.Types;
import database.commands.Update;
import database.commands.UserUpdates;

import java.sql.*;

public class main {
    public static void main(String[] args) throws SQLException {
        Update uss=new UserUpdates();

        UserUpdates us = new UserUpdates();
        us.signUp("aouldcott0", Types.EntityType.USER, "Wanstall", "Accountant I", "www.picture", "zC2@3q~Av", "06416856140", "Ouldcott");

        // MessageCommand messageCommand = new MessageCommand();
        // messageCommand.sendMessage(2,1,"salam", MessageCommand.MessageType.TEXT);
//        messageCommand.sendMessage(3,1,"salam,khubi?", MessageCommand.MessageType.TEXT);
//        messageCommand.sendMessage(1,4,"pic1", MessageCommand.MessageType.IMAGE);
        //messageCommand.editMessage(1,"hello");
        //messageCommand.sendMessage(2,1,"ch khbr", MessageCommand.MessageType.TEXT);
        //messageCommand.deleteMessage(4);

    }


}
