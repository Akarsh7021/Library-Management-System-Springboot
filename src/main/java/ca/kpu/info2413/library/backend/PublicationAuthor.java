package ca.kpu.info2413.library.backend;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class PublicationAuthor
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "author_id_Author")
    private Integer authorIdAuthor;

    @Column(name = "isbn_13_Publication")
    private Integer isbn13Publication;
}
