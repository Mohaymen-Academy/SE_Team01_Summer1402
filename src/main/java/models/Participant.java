package models;

import jakarta.persistence.*;

@Entity
@Table
public class Participant {

    @ManyToOne
    @JoinColumn(name = "profile1_id", referencedColumnName = "id")
    private Profile profile1;

    @ManyToOne
    @JoinColumn(name = "profile2_id", referencedColumnName = "id")
    private Profile profile2;


    @ManyToOne
    @JoinColumn(name = "message_id", referencedColumnName = "ID")
    private Message message;

    public Participant() {}

    public Participant(Profile profile1, Profile profile2, Message message) {
        this.profile1 = profile1;
        this.profile2 = profile2;
        this.message = message;
    }
}
