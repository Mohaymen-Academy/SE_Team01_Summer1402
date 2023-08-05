package commands;

import DataBaseApi.Connector;
import Types.profileType;
import lombok.SneakyThrows;
import models.Profile;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.File;
import java.io.FileInputStream;
import java.time.Instant;

public class ProfileCommand {

    private static SessionFactory sf = Connector.getSessionFactory();

    public static void addGroup(String name, String bio, String pic_link) {
        Session session = sf.openSession();
        session.beginTransaction();
        Profile profile = new Profile(name, bio, null, Instant.now(), null, profileType.GROUP);
        session.persist(profile);
        session.getTransaction().commit();
        session.close();

    }

    public static void addChannel(String name, String bio, String pic_link) {

    }
}
