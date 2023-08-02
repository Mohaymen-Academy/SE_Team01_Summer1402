package models;
import Types.profileType;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "Profile")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String bio;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "username", referencedColumnName = "username")
    private Account account;

    @CreationTimestamp
    private Instant createdOn;

    private profileType profileType;

    public Profile() {

    }

    public Profile(String name, String bio, Account account,  profileType entityType) {
        this.name = name;
        this.bio = bio;
        this.account = account;
        profileType =entityType;

    }
}
