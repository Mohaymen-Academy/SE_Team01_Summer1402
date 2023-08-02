import Types.profileType;
import models.Account;
import models.Message;
import models.Participant;
import models.Profile;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.time.Instant;
import java.time.LocalTime;

public class Main {

    public static void main(String[] args) {

        Configuration config = new Configuration();
        config.configure();
        SessionFactory sessionFactory = config.buildSessionFactory();

        Account account = new Account("a", "1234", "09121234567");
        Profile profile = new Profile("a", "a", account, null, profileType.USER);

        Account account2 = new Account("b", "1234", "09127654321");
        Profile profile2 = new Profile("b", "b", account2, null, profileType.USER);

        Message message = new Message(profile, profile2, Instant.now(), "hello", null, null);

        Participant participant = new Participant(profile, profile2, null);

        Session session = sessionFactory.openSession();


        session.beginTransaction();

        session.persist(profile);
        session.persist(account);

        session.persist(account2);
        session.persist(profile2);

        session.persist(participant);

        session.persist(message);


        session.getTransaction().commit();
    }
}
