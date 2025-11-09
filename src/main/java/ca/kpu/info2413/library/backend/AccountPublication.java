package ca.kpu.info2413.library.backend;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class AccountPublication
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "waitlist_position")
    private Integer waitlistPosition;

    @Column(name = "account_id_Account")
    private Integer accountIdAccount;

    @Column(name = "isbn_13_Publication")
    private Integer isbn13Publication;

    // for JPA only, no use
    public AccountPublication() {
    }

    // getters, setters and constructor
}
