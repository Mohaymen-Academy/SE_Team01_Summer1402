package models;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "Account")
public class Account implements Serializable {
    @Id
    @Column(nullable = false, length = 50)
    private String username;

    @Column(nullable = false, name = "password",length = 255)
    private String password;

    @Column(nullable = false, unique = true, name = "phoneNumber", length = 13)
    private String phoneNumber;

    @OneToOne(mappedBy = "Account")
    private Profile profile;

    public Account() {

    }

    public Account(String username, String password, String phoneNumber) {
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }
}
