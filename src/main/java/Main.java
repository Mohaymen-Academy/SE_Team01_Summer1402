import Types.profileType;
import commands.MessageCommand;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import models.Account;
import models.Message;
import models.Participant;
import models.Profile;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.time.Instant;
import java.time.LocalTime;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Configuration config = new Configuration();
        config.configure();
        SessionFactory sessionFactory = config.buildSessionFactory();

        Account account = new Account("a", "1234", "09121234567");
        Profile profile = new Profile("a", "a", account, null, profileType.USER);

        Account account2 = new Account("b", "1234", "09127654321");
        Profile profile2 = new Profile("b", "b", account2, null, profileType.USER);

//        Message message = new Message(profile, profile2, Instant.now(), "hello", null, null);

//        Participant participant = new Participant(profile, profile2, null);

        Session session = sessionFactory.openSession();

//        CriteriaBuilder cb = session.getCriteriaBuilder();
//        CriteriaQuery<Profile> cq = cb.createQuery(Profile.class);
//        Root<Profile> root = cq.from(Profile.class);
//        CriteriaQuery<Profile> all = cq.select(root).where(cb.equal(root.get("account"), "a"));
//        List<Profile> profiles = session.createQuery(all).getResultList();
//        profile = profiles.get(0);
//        all = cq.select(root).where(cb.equal(root.get("account"), "a"));
//        profiles = session.createQuery(all).getResultList();
//        profile2 = profiles.get(0);

        session.beginTransaction();


        Message message = new Message(profile, profile2, Instant.now(), "hello", null, null);


//        session.persist(profile);
//        session.persist(account);

//        session.persist(account2);
//        session.persist(profile2);
//
//        session.persist(participant);
//
//        session.persist(message);


        session.getTransaction().commit();


        MessageCommand messageCommand = new MessageCommand();
//        messageCommand.sendMessage(message);
    }
}
