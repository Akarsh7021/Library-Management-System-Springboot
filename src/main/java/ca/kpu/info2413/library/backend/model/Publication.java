package ca.kpu.info2413.library.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "Publication")
@Data
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Publication
{
    @Id
    @Column(name = "isbn_13")
    private Integer isbn13;

    private String title;

    @Column(name = "publication_date")
    private LocalDate publicationDate;

    @Column(name = "page_count")
    private Integer pageCount;

    private String genre;

    @OneToMany(mappedBy = "publication", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<PublicationAuthor> publicationAuthors;

    // Serialize authors to JSON
    @JsonProperty(value = "authors", access = JsonProperty.Access.READ_ONLY)
    public List<String> getAuthors() {
        if (publicationAuthors == null) return List.of();
        return publicationAuthors.stream()
                .map(pa -> pa.getAuthor().getAuthorName())
                .toList();
    }

    // Accept authors on deserialization and convert to PublicationAuthor entries
    @JsonProperty("authors")
    public void setAuthors(List<String> authors) {
        if (authors == null) {
            this.publicationAuthors = new ArrayList<>();
            return;
        }
        // Defensive: create new list and PublicationAuthor/Author objects (adjust per your model)
        List<PublicationAuthor> paList = new ArrayList<>();
        for (String name : authors) {
            if (name == null || name.isBlank()) continue;
            Author a = new Author();
            a.setAuthorName(name.trim());
            PublicationAuthor pa = new PublicationAuthor();
            pa.setAuthor(a);
            pa.setPublication(this);
            paList.add(pa);
        }
        this.publicationAuthors = paList;
    }
}
