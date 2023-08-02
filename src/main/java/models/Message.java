package models;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "MESSAGES")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int sender;
    private int receiver; // receiver could be a user or a group or a channel
    @Column(name = "FULL_DATE")
    private Timestamp fullDate;
    private String messageText;
    private String messageByte;
    private String fileExtension;

    public Message() {

    }

    public Message(int id, int sender, int receiver,
                   Timestamp fullDate, String messageText,
                   String messageByte, String fileExtension) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.fullDate = fullDate;
        this.messageText = messageText;
        this.messageByte = messageByte;
        this.fileExtension = fileExtension;
    }
}