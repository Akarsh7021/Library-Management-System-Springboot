package ca.kpu.info2413.library.backend;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class ManyPublicationHasManyPublication {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int isbn_13_publication;
    private int isbn_13_publication1;
    private BigDecimal price;
    private LocalDate publishDate;

    // for JPA only, no use
    public ManyPublicationHasManyPublication() {
    }

    // getters, setters and constructor
}
