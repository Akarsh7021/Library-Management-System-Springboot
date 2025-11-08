package ca.kpu.info2413.library.backend;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;

@Entity
@IdClass(ManyPublicationHasManyPublication.ManyPublicationHasManyPublicationId.class)
public class ManyPublicationHasManyPublication {
    @Id
    @Column(name = "isbn_13_publication")
    private Integer isbn13Publication;

    @Id
    @Column(name = "isbn_13_publication1")
    private Integer isbn13Publication1;

    // Extra columns not in data dictionary - flag for human review
    // @Column(name = "price")
    // private BigDecimal price;
    // @Column(name = "publishDate")
    // private LocalDate publishDate;

    @ManyToOne
    @JoinColumn(name = "isbn_13_publication", insertable = false, updatable = false)
    private Publication publication;

    @ManyToOne
    @JoinColumn(name = "isbn_13_publication1", insertable = false, updatable = false)
    private Publication publication1;

    // for JPA only, no use
    public ManyPublicationHasManyPublication() {
    }

    // getters, setters and constructor

    public static class ManyPublicationHasManyPublicationId implements Serializable {
        private Integer isbn13Publication;
        private Integer isbn13Publication1;

        // getters, setters, equals, hashCode
    }
}
