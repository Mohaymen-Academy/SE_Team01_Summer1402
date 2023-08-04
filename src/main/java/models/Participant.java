package models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "Participant")
public class Participant {

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile1_id", referencedColumnName = "profile_id")
    @Getter
    private Profile profile1;

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile2_id", referencedColumnName = "profile_id")
    @Getter
    private Profile profile2;


    @Setter
    @ManyToOne
//    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "lastSeen_message_id", referencedColumnName = "message_id")
    private Message message;



    public Participant() {}

    public Participant(Profile profile1, Profile profile2, Message message) {
        this.profile1 = profile1;
        this.profile2 = profile2;
        this.message = message;
    }
}
