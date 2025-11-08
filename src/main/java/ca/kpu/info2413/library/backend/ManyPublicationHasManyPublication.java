package ca.kpu.info2413.library.backend;

import jakarta.persistence.Column;
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
    @Column(name = "isbn_13_publication")
    private Integer isbn13Publication;

    @Column(name = "isbn_13_publication1")
    private Integer isbn13Publication1;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "publishDate")
    private LocalDate publishDate;

    // for JPA only, no use
    public ManyPublicationHasManyPublication() {
    }

    // getters, setters and constructor
}
