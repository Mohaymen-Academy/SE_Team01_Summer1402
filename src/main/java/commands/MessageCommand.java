package commands;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import models.Message;
import models.Participant;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageCommand {

    private final SessionFactory sessionFactory;

    public MessageCommand() {
        Configuration config = new Configuration();
        config.configure();
        sessionFactory = config.buildSessionFactory();
    }

    private void persist(Object object) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.persist(object);
        session.getTransaction().commit();
        session.close();
    }

    public void sendMessage(Message message) {
        Participant participant = new Participant(message.getSender(), message.getReceiver(), null);


        //todo : fix this section
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Participant participant1 = session.find(Participant.class, (Object) message.getSender().getID(),
                (Map<String, Object>) new HashMap<>().put("profile2_id", message.getReceiver().getID()));
        if (participant1 == null) persist(participant);
        session.getTransaction().commit();
        session.close();


        persist(message);
    }

    public void editMessage(int messageID, String messageText) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Message message = session.find(Message.class, messageID);
        message.setMessageText(messageText);
        session.merge(message);
        session.getTransaction().commit();
        session.close();
    }

    public void deleteMessage(int messageID) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Message message = session.find(Message.class, messageID);
        session.remove(message);
        session.getTransaction().commit();
        session.close();
    }

    public void getAllMessages(int sender) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Message> cq = cb.createQuery(Message.class);
        Root<Message> root = cq.from(Message.class);
        CriteriaQuery<Message> all = cq.select(root).where(cb.equal(root.get("fk_sender"), sender));
        List<Message> messages = session.createQuery(all).getResultList();

        session.getTransaction().commit();
        session.close();

        messages.forEach(System.out::println);
    }


    public void getAllMessagesNum(int sender) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Message> root = cq.from(Message.class);
        cq.select(cb.count(root));
        cq.where(cb.equal(root.get("fk_sender"), sender));

        session.getTransaction().commit();
        session.close();
    }


    

}
