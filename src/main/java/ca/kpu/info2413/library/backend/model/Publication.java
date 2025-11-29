package ca.kpu.info2413.library.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Publication")
@Data
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Publication
{
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "isbn_13")
    private Long isbn13;

    private String title;

    @Column(name = "publication_date")
    private LocalDate publicationDate;

    @Column(name = "page_count")
    private Integer pageCount;

    private String genre;

    @OneToMany(mappedBy = "publication", cascade = jakarta.persistence.CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<PublicationAuthor> publicationAuthors;

    @Transient
    @JsonProperty("authors")
    private List<String> authors;

    public List<String> getInputAuthors()
    {
        return authors;
    }

    // Derived property for JSON
    @JsonProperty("authors")
    public List<String> getAuthors()
    {
        if (publicationAuthors == null) return List.of();
        return publicationAuthors.stream()
                .map(pa -> pa.getAuthor().getAuthorName())
                .toList();
    }

    public Publication(Long isbn13)
    {
        this.isbn13 = isbn13;
    }
}
