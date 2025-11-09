package ca.kpu.info2413.library.backend;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Builder
public class PublicationPublication
{
    @EmbeddedId
    private PublicationPublicationId id;

    @MapsId("isbn13Publication")
    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name = "isbn_13_publication")
    private Publication publication;

    @MapsId("isbn13Publication1")
    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name = "isbn_13_publication1")
    private Publication publication1;

}
