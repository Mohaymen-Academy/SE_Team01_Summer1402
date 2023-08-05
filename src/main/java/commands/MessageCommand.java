package commands;

import DataBaseApi.Connector;
import Types.profileType;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import models.Message;
import models.Participant;
import models.Profile;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.Instant;
import java.util.ArrayList;
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
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(root.get("profile1"), sender_id));
        predicates.add(cb.equal(root.get("profile2"), receiver_id));
        CriteriaQuery<Participant> all = cq.select(root).where(predicates.toArray(new Predicate[]{}));
        List<Participant> participants = session.createQuery(all).getResultList();
        session.close();
        return participants.size() != 0;
    }


    public void sendMessage(long sender_id, long receiver_id, String msg_text, byte[] fileContent, String fileExtension) {
        Session session = sf.openSession();
        session.beginTransaction();
        Profile sender = session.get(Profile.class, sender_id);
        Profile receiver = session.get(Profile.class, receiver_id);
        Message message = new Message(sender, receiver, Instant.now(), msg_text, fileContent, fileExtension);
        if (!check_if_chat_exist(sender_id, receiver_id)) {
            Participant newParticipant1 = new Participant(message.getSender(), message.getReceiver(), null);
            //todo: null message for participant2?
            Participant newParticipant2 = new Participant(message.getReceiver(), message.getSender(), null);
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
//
//
//        Profile profile1 = session.get(Profile.class, message.getSender().getID());
//        profile1.getSentMessages().remove(message);
//        Profile profile2 = session.get(Profile.class, message.getReceiver().getID());
//        profile2.getReceivedMessages().remove(message);
//
//        CriteriaBuilder cb = session.getCriteriaBuilder();
//        CriteriaQuery<Participant> cq = cb.createQuery(Participant.class);
//        Root<Participant> root = cq.from(Participant.class);
//        CriteriaQuery<Participant> all = cq.select(root).where(cb.equal(root.get("message"), messageID));
//        List<Participant> participants = session.createQuery(all).getResultList();
//        participants.forEach(p -> p.setMessage(null)); //todo : fix this part
//        participants.forEach(session::merge);


////        session.remove(message);
//        session.merge(profile1);
//        session.merge(profile2);
//
//        session.getTransaction().commit();
    //    session.beginTransaction();
        session.remove(message);
        session.getTransaction().commit();
        session.close();
    }

    public double avgMessagesSentBy(long sender_id) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.getTransaction().commit();
        Query query = session.createQuery("select count(distinct M.sender) from Message as M", Message.class);
        int size =  Integer.parseInt(query.getResultList().get(0).toString());
        return (double) (getCountMessagesBy(sender_id) / size);
    }

    public Set<Message> getAllMessages(long profile_id) {
        Session session = sf.openSession();
        Profile profile = session.get(Profile.class, profile_id);
        Hibernate.initialize(profile.getSentMessages());
        Set<Message> messageSet = profile.getSentMessages();
        session.close();
        return messageSet;

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
        return Ids.stream().filter(x -> x > message_id).count();

    }


    public long getCountMessagesBy(long profile_id) {
        return getAllMessages(profile_id).size();
    }


}
