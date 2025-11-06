package ca.kpu.info2413.library.backend;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int author_id;
    private String author_name;


    // for JPA only, no use
    public Author() {
    }

    // getters, setters and constructor
}
