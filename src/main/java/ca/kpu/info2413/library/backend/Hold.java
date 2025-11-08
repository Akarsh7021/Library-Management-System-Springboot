package ca.kpu.info2413.library.backend;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Hold {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "book_hold_id")
    private Integer bookHoldId;

    @Column(name = "hold_expiry")
    private LocalDate holdExpiry;

    @Column(name = "held_since", columnDefinition = "DATE DEFAULT CURRENT_DATE")
    private LocalDate heldSince;

    @Column(name = "serial_barcode_BookCopy")
    private Integer serialBarcodeBookCopy;

    @Column(name = "account_id_Account")
    private Integer accountIdAccount;

    // for JPA only, no use
    public Hold() {
    }

    // getters, setters and constructor
}
