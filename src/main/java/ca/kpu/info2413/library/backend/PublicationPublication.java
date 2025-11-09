package ca.kpu.info2413.library.backend;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "PublicationPublication")
@Getter
@Setter
@ToString(exclude = {"publication", "publication1"}) // avoid lazy-loading / recursion in toString
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA requires a no-arg ctor (protected is fine)
@AllArgsConstructor
@Builder
public class PublicationPublication
{
    @EmbeddedId
    private PublicationPublicationId id;

    @MapsId("isbn13Publication")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "isbn_13_publication")
    private Publication publication;

    @MapsId("isbn13Publication1")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "isbn_13_publication1")
    private Publication publication1;


    public PublicationPublication(Publication publication, Publication publication1)
    {
        this.publication = publication;
        this.publication1 = publication1;
        this.id = new PublicationPublicationId(publication != null ? publication.getIsbn13() : null,           // adjust if your getter is getId()
                publication1 != null ? publication1.getIsbn13() : null // adjust if different
        );
    }
}
