package ca.kpu.info2413.library.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public final class PublicationAuthorId implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Column(name = "author_id_Author", nullable = false)
    private Integer authorIdAuthor;

    @Column(name = "isbn_13_Publication", nullable = false)
    private Long isbn13Publication;
}