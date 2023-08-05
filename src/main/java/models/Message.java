package models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;

import java.time.Instant;
import java.util.Set;

@ToString
@Entity
@Table(name = "Message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    @Getter
    private int message_id;

    @Getter
    @ManyToOne()
    @JoinColumn(name = "fk_sender", referencedColumnName = "profile_id", nullable = false)
    private Profile sender;

    @Getter
    @ManyToOne()
    @JoinColumn(name = "fk_receiver", referencedColumnName = "profile_id", nullable = false)
    private Profile receiver; // receiver could be a user or a group or a channel

    @CreationTimestamp
    @Column(name = "sent_at", nullable = false)
    private Instant sent_at;

    @Getter
    @Column(name = "Message_text")
    private String messageText;

    @Column(name = "fileContent")
    private byte[] fileContent;

    @Column(name = "file_extension")
    private String fileExtension;


    public Message() {
    }

    public Message(Profile sender, Profile receiver,
                   Instant sent_at, String messageText,
                   byte[] fileContent, String fileExtension) {
        this.sender = sender;
        this.receiver = receiver;
        this.sent_at = sent_at;
        this.messageText = messageText;
        this.fileContent = fileContent;
        this.fileExtension = fileExtension;
    }

    public Profile getSender() {
        return sender;
    }

    public Profile getReceiver() {
        return receiver;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }
}