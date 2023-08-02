package models;

import jakarta.persistence.*;

@Entity
@Table(name = "Participant")
public class Participant {

    @Id
    @ManyToOne
    @JoinColumn(name = "profile1_id", referencedColumnName = "profile_id")
    private Profile profile1;

    @Id
    @ManyToOne
    @JoinColumn(name = "profile2_id", referencedColumnName = "profile_id")
    private Profile profile2;


    @ManyToOne
    @JoinColumn(name = "message_id", referencedColumnName = "message_id")
    private Message message;

    public Participant() {}

    public Participant(Profile profile1, Profile profile2, Message message) {
        this.profile1 = profile1;
        this.profile2 = profile2;
        this.message = message;
    }
}
