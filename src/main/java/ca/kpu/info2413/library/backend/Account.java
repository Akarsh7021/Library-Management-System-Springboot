package ca.kpu.info2413.library.backend;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "account_id")
    private Integer accountId;

    @Column(name = "password_hash")
    private String passwordHash;

    @Column(name = "account_type")
    private String accountType;

    @Column(name = "notification_email")
    private String notificationEmail;

    @Column(name = "phone_number", nullable = true)
    private String phoneNumber;

    @Column(name = "full_name")
    private String fullName;

    // for JPA only, no use
    public Account() {
    }

    // getters, setters and constructor
}
