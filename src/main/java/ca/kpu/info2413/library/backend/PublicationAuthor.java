package ca.kpu.info2413.library.backend;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "PublicationAuthor")
@Getter
@Setter
@ToString(exclude = {"author", "publication"}) // avoid lazy-loading / recursion in toString
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA requires a no-arg ctor (protected is fine)
@AllArgsConstructor
@Builder
public class PublicationAuthor {

    @EmbeddedId
    private PublicationAuthorId id;

    @MapsId("authorIdAuthor")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id_Author", nullable = false)
    private Author author;

    @MapsId("isbn13Publication")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "isbn_13_Publication", nullable = false)
    private Publication publication;


    public PublicationAuthor(Author author, Publication publication) {
        this.author = author;
        this.publication = publication;
        this.id = new PublicationAuthorId(
                author != null ? author.getAuthorId() : null,           // adjust if your getter is getId()
                publication != null ? publication.getIsbn13() : null // adjust if different
        );
    }
}