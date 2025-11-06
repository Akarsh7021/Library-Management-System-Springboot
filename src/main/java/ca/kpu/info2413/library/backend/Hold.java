package ca.kpu.info2413.library.backend;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Hold {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int book_hold_id;
    private java.sql.Date hold_expiry;
    private java.sql.Date held_since;
    private int serial_barcode_BookCopy;
    private int account_id_Account;

    // for JPA only, no use
    public Hold() {
    }

    // getters, setters and constructor
}
