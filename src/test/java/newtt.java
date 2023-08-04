import models.Account;
import models.Profile;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class newtt {
    private SessionFactory factory;

    @BeforeEach
    public void setup() {
        Configuration config = new Configuration();
        config.configure();
        factory = config.buildSessionFactory();
    }

    @Test

    public void addUser() {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Account account = new Account("sana", "1234", "+989124147656");
            session.persist(account);
//            Profile profile =new Profile()
//            session.persist(profile);
//            account.setProfile(profile);
//            session.persist(account);
            tx.commit();
        } catch (HibernateException ex) {
            if (tx != null)
                tx.rollback();
        } finally {
            session.close();
        }
    }
}
