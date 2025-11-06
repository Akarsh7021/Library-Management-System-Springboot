package ca.kpu.info2413.library.backend;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class ManyAccountHasManyPublication {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int waitlist_position;
    private int account_id_Account;
    private int isbn_13_Publication;

    // for JPA only, no use
    public ManyAccountHasManyPublication() {
    }

    // getters, setters and constructor
}
