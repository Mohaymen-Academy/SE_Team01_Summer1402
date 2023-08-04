package DataBaseApi;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Connector {
    private static final String url = "jdbc:postgresql://localhost:5432/Messenger";
    private static final String user = "postgres";
    private static final String password = "Sara.5560734055";


    private static SessionFactory sessionFactory;

    private Connector() {
        Configuration config = new Configuration();
        config.configure();
        sessionFactory = config.buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null)
            new Connector();
        return sessionFactory;
    }
}
