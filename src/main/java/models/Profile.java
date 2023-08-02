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

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "biography")
    private String bio;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "FK_username", referencedColumnName = "username")
    private Account account;

    @Column(name = "createdOn", nullable = false)
    @CreationTimestamp
    private Instant createdOn;

    @Column(name = "image_file")
    private byte[] image;

    @Column(name = "ProfileType", nullable = false)
    private profileType profileType;

    public Profile() {

    }

    public Profile(String name, String bio, Account account, byte[] image, Types.profileType profileType) {
        this.name = name;
        this.bio = bio;
        this.account = account;
        this.image = image;
        this.profileType = profileType;
    }
}
