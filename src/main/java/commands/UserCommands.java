package commands;

import DataBaseApi.Connector;
import Types.profileType;

import lombok.SneakyThrows;
import models.Account;
import models.Profile;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.io.File;
import java.io.FileInputStream;
import java.time.Instant;

public class UserCommands {
    private static SessionFactory sf = Connector.getSessionFactory();

    @SneakyThrows
    public static byte[] convertToByteArray(String path) {
        File file = new File(path);
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] fileContent = new byte[(int) file.length()];
        fileInputStream.read(fileContent);
        return fileContent;
    }


    public static void signUp(String username, String name, String bio, String pic_link, String password, String phoneNumber) {
        if (!check_valid_phoneNumber(phoneNumber)) {
            System.out.println("Not valid phone number");
            return;
        }
        if (check_if_username_exist(username)) {
            System.out.println("This username is already assigned, Be careful!");
            return;
        }
        Account account = new Account(username, password, phoneNumber);
        Profile profile = new Profile(name, bio, account, Instant.now(), convertToByteArray(pic_link), profileType.USER);
        account.setProfile(profile);
        Session session = sf.openSession();
        session.beginTransaction();
        session.persist(account);
        session.persist(profile);
        session.getTransaction().commit();
    }

    public static void delete_account(String username) {
        Session session = sf.openSession();
        session.beginTransaction();
        Account account = session.find(Account.class, username);
        session.remove(account);
        session.getTransaction().commit();
        session.close();
    }

    //to do not working
    public static void change_biography(String username, String new_bio) {
        Session session = sf.openSession();
        session.beginTransaction();
        //  Account account = session.find(Account.class, username);
        Profile profile = session.find(Profile.class, username);
        // profile.setBio(new_bio);
//        session.update(profile);
//        session.getTransaction().commit();
        session.close();
    }

    private static boolean check_if_username_exist(String username) {
        Session session = sf.openSession();
        session.beginTransaction();
        Account account = session.find(Account.class, username);
        session.close();
        return account != null;
    }

    public static boolean login_check(String username, String password) {
        if (!check_if_username_exist(username)) {
            System.out.println("This username is not valid");
            return false;
        }
        Session session = sf.openSession();
        session.beginTransaction();
        Account account = session.find(Account.class, username);
        return account.getPassword().equals(password);
    }

    private static boolean check_valid_phoneNumber(String phoneNumber) {
        return phoneNumber.matches("^\\+989\\d{9}");
    }


}
