package ca.kpu.info2413.library.backend;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Publication {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int isbn_13;
    private String title;
    private java.sql.Date publication_date;
    private int page_count;
    private String genre;

    // for JPA only, no use
    public Publication() {
    }

    // getters, setters and constructor
}
