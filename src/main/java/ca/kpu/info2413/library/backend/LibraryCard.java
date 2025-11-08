package ca.kpu.info2413.library.backend;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class LibraryCard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "card_number")
    private Integer cardNumber;

    private boolean valid;

    @Column(name = "account_id_Account")
    private Integer accountIdAccount;

    // for JPA only, no use
    public LibraryCard() {
    }

    // getters, setters and constructor
}