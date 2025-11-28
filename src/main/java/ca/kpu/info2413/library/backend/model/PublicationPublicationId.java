package ca.kpu.info2413.library.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public final class PublicationPublicationId implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Column(name = "isbn_13_publication")
    private Long isbn13Publication;

    @Column(name = "isbn_13_publication1")
    private Long isbn13Publication1;

    public PublicationPublicationId(Long integer, Long integer1)
    {
    }
}
