package models;

import Types.profileType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;
import java.util.Set;


@ToString
@Entity
@Table(name = "Profile")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private Long profile_id;

    @Column(name = "name", nullable = false)
    private String name;

    @Setter
    @Column(name = "biography")
    private String bio;

    @OneToOne
  //  @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "fk_username", referencedColumnName = "username")
    private Account account;

    @Column(name = "createdOn", nullable = false)
    @CreationTimestamp
    private Instant createdOn;

    @Column(name = "image_file")
    private byte[] image;

    @Getter
    @Column(name = "ProfileType", nullable = false)
    private profileType profileType;

    @Getter
    @OneToMany(mappedBy = "sender")
    private Set<Message> sentMessages;

    @OneToMany(mappedBy = "receiver")
    private Set<Message> receivedMessages;

    @Getter
    @OneToMany(mappedBy = "profile1")
    private Set<Participant> connections;


    public Profile() {

    }

    public Profile(String name, String bio, Account account, Instant createdOn, byte[] image, Types.profileType profileType) {
        this.name = name;
        this.bio = bio;
        this.account = account;
        this.createdOn = createdOn;
        this.image = image;
        this.profileType = profileType;
    }

    public Long getID() {
        return this.profile_id;
    }
}
