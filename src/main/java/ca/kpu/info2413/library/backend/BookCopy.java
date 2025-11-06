package ca.kpu.info2413.library.backend;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class BookCopy {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int serial_barcode;
    private java.sql.Date date_acquired;
    private int isbn_13_Publication;

    // for JPA only, no use
    public BookCopy() {
    }

    // getters, setters and constructor
}

