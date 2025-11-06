package ca.kpu.info2413.library.backend;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class ManyAuthorHasManyPublication {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int author_id_Author;
    private int isbn_13_Publication;


    // for JPA only, no use
    public ManyAuthorHasManyPublication() {
    }

    // getters, setters and constructor
}
