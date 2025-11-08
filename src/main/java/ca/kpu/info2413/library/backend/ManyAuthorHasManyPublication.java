package ca.kpu.info2413.library.backend;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;

@Entity
@IdClass(ManyAuthorHasManyPublication.ManyAuthorHasManyPublicationId.class)
public class ManyAuthorHasManyPublication {
    @Id
    @Column(name = "author_id_Author")
    private Integer authorIdAuthor;

    @Id
    @Column(name = "isbn_13_Publication")
    private Integer isbn13Publication;

    @ManyToOne
    @JoinColumn(name = "author_id_Author", insertable = false, updatable = false)
    private Author author;

    @ManyToOne
    @JoinColumn(name = "isbn_13_Publication", insertable = false, updatable = false)
    private Publication publication;

    // for JPA only, no use
    public ManyAuthorHasManyPublication() {
    }

    // getters, setters and constructor

    public static class ManyAuthorHasManyPublicationId implements Serializable {
        private Integer authorIdAuthor;
        private Integer isbn13Publication;

        // getters, setters, equals, hashCode
    }
}
