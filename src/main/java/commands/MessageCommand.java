package commands;

import DataBaseApi.Connector;
import Types.profileType;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import models.Message;
import models.Participant;
import models.Profile;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.Instant;
import java.util.List;
import java.util.Set;

public class MessageCommand {

    private static SessionFactory sf = Connector.getSessionFactory();

    private void persist(Object object) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.persist(object);
        session.getTransaction().commit();
        session.close();
    }

    public boolean check_if_chat_exist(long sender_id, long receiver_id) {
        Session session = sf.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Participant> cq = cb.createQuery(Participant.class);
        Root<Participant> root = cq.from(Participant.class);
        CriteriaQuery<Participant> all = cq.select(root)
                .where(cb.equal(root.get("profile1"), sender_id))
                .where(cb.equal(root.get("profile2"), receiver_id));
        List<Participant> participants = session.createQuery(all).getResultList();
        session.close();
        return participants.size() != 0;
    }


//    public void sendMessage(Message message) {
//        Session session = sf.openSession();
//        session.beginTransaction();
//        if (!check_if_chat_exist(message.getSender(), message.getReceiver())) {
//            Participant newParticipant1 = new Participant(message.getSender(), message.getReceiver(), null);
//            Participant newParticipant2 = new Participant(message.getReceiver(), message.getSender(), message);
//            session.persist(newParticipant1);
//            session.persist(newParticipant2);
//        }
//        session.persist(message);
//        session.getTransaction().commit();
//        session.close();
//    }

    public void sendMessage(long sender_id, long receiver_id, String msg_text, byte[] fileContent, String fileExtension) {
        Session session = sf.openSession();
        session.beginTransaction();
        Profile sender = session.get(Profile.class, sender_id);
        Profile receiver = session.get(Profile.class, receiver_id);
        Message message = new Message(sender, receiver, Instant.now(), msg_text, fileContent, fileExtension);
        if (!check_if_chat_exist(sender_id, receiver_id)) {
            Participant newParticipant1 = new Participant(message.getSender(), message.getReceiver(), null);
            //todo: null message for participant2?
            Participant newParticipant2 = new Participant(message.getReceiver(), message.getSender(), message);
            session.persist(newParticipant1);
            session.persist(newParticipant2);
        }
        session.persist(message);
        session.getTransaction().commit();
        session.close();
    }

    public void editMessage(int messageID, String messageText) {
        Session session = sf.openSession();
        session.beginTransaction();
        Message message = session.find(Message.class, messageID);
        message.setMessageText(messageText);
        session.merge(message);
        session.getTransaction().commit();
        session.close();
    }

    public void deleteMessage(int messageID) {
        Session session = sf.openSession();
        session.beginTransaction();
        Message message = session.find(Message.class, messageID);
        session.remove(message);
        session.getTransaction().commit();
        session.close();
    }

    public double avgMessagesSentBy(long sender_id) {
        Session session = sf.openSession();
        Query query = session.createQuery("select count(*) from Message where Message .sender =:sender_id", Message.class);
        query.setParameter("sender_id", sender_id);
        return (double) (getCountMessagesBy(sender_id) / query.getFirstResult());
    }

    public Set<Message> getAllMessages(long profile_id) {
        Session session = sf.openSession();
        Profile profile = session.get(Profile.class, profile_id);
        Set<Message> messageSet = profile.getSentMessages();
        session.close();
        return messageSet;

//        session.beginTransaction();
//
//        CriteriaBuilder cb = session.getCriteriaBuilder();
//        CriteriaQuery<Message> cq = cb.createQuery(Message.class);
//        Root<Message> root = cq.from(Message.class);
//        CriteriaQuery<Message> all = cq.select(root).where(cb.equal(root.get("fk_sender"), sender));
//        List<Message> messages = session.createQuery(all).getResultList();
//
//        session.getTransaction().commit();
//        session.close();
//
//        messages.forEach(System.out::println);
    }

    public long getUsersInTouchWith(long profile_id) {
        Session session = sf.openSession();
        Profile profile = session.get(Profile.class, profile_id);
        Set<Participant> allChats = profile.getConnections();
        long counter = 0;
        for (Participant p : allChats) {
            if (p.getProfile2().getProfileType() != profileType.USER) {
                counter += p.getProfile2().getConnections().size();
            } else
                counter++;
        }
        return counter;
    }

    public long MessageViews(long message_id) {
        Session session = sf.openSession();
        Message msg = session.find(Message.class, message_id);
        Query query;
        if (msg.getReceiver().getProfileType() == profileType.USER) {
            query = session.createQuery("select Message.id from Participant p where p.profile1.id=:receiver_id and p.profile2.id=:sender_id", Long.class);
            query.setParameter("receiver_id", msg.getReceiver().getID());
            query.setParameter("sender_id", msg.getSender().getID());
        } else {
            query = session.createQuery("select Message.id from Participant p where p.profile2.id=:receiver_id and p.profile2.profileType!=:profType", Long.class);
            query.setParameter("receiver_id", msg.getReceiver().getID());
            query.setParameter("profType", profileType.USER);
        }
        List<Long> Ids = query.getResultList();
       // long counter = 0;
        return Ids.stream().filter(x -> x > message_id).count();
        // return counter;
    }


    public long getCountMessagesBy(long profile_id) {
        return getAllMessages(profile_id).size();
//        Session session = sessionFactory.openSession();
//        session.beginTransaction();
//
//        CriteriaBuilder cb = session.getCriteriaBuilder();
//        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
//        Root<Message> root = cq.from(Message.class);
//
//        cq.select(cb.count(root));
//        cq.where(cb.equal(root.get("fk_sender"), sender));
//
//        session.getTransaction().commit();
//        session.close();
    }


}
