import commands.MessageCommand;
import commands.ProfileCommand;
import commands.UserCommands;
import models.Account;
import models.Message;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.sql.Connection;
import java.time.Instant;

public class Main {

    public static void main(String[] args) {
        UserCommands.signUp("sana", "sana", "working", "C:\\Users\\Asus\\Pictures\\Saved Pictures\\unnamed.jpg", "123456", "+989124147656");
        UserCommands.signUp("sara", "sana", "nothing to say", "C:\\Users\\Asus\\Pictures\\Saved Pictures\\unnamed.jpg", "123456", "+989124147665");
        UserCommands.signUp("maryam", "maryam", null, "C:\\Users\\Asus\\Pictures\\Saved Pictures\\unnamed.jpg", "123456", "+989124147623");
        UserCommands.signUp("xxx", "somayye", null, "C:\\Users\\Asus\\Pictures\\Saved Pictures\\unnamed.jpg", "123456", "+989124147632");
        UserCommands.signUp("boz", "boz", "studying", "C:\\Users\\Asus\\Pictures\\Saved Pictures\\unnamed.jpg", "123456", "+989124147629");
        UserCommands.signUp("sag", "sag", "sleeping", "C:\\Users\\Asus\\Pictures\\Saved Pictures\\unnamed.jpg", "123456", "+989124147692");



      //  creating messages and chats
        MessageCommand msgcommand = new MessageCommand();
        msgcommand.sendMessage(1L, 2L, "salam chtori", null, null);
        msgcommand.sendMessage(1L, 2L, "migma", null, null);
       // msgcommand.editMessage(2, "che khabar");
        msgcommand.sendMessage(3L, 1L, "hellooooo", null, null);
        System.out.println(msgcommand.getAllMessages(3));

        msgcommand.deleteMessage(3);
        System.out.println(msgcommand.getAllMessages(3));
  //      msgcommand.getAllMessages(3).forEach(m -> System.out.println(m.getMessageText()));
    //    System.out.println(msgcommand.getCountMessagesBy(1));
      //  System.out.println(msgcommand.getUsersInTouchWith(1));
        //System.out.println(msgcommand.avgMessagesSentBy(1));
        //UserCommands.change_biography(3, "working");
       // ProfileCommand.addGroup("group1","hihihi",null);
        // msgcommand.getAllMessages(1);
        //System.out.println(msgcommand.getUsersInTouchWith(1));
        // UserCommands.change_biography("sana","hola");
    }
}
