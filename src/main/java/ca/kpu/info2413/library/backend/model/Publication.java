package ca.kpu.info2413.library.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class Publication
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "isbn_13")
    private Integer isbn13;

    private String title;

    @Column(name = "publication_date")
    private LocalDate publicationDate;

    @Column(name = "page_count")
    private Integer pageCount;

    private String genre;
}
