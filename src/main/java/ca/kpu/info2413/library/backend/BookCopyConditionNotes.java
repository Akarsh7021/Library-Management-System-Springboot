package ca.kpu.info2413.library.backend;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class BookCopyConditionNotes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String condition_notes;
    private int serial_barcode_BookCopy;

    // for JPA only, no use
    public BookCopyConditionNotes() {
    }

    // getters, setters and constructor
}