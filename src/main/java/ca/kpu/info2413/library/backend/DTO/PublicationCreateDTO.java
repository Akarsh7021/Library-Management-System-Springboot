package ca.kpu.info2413.library.backend.DTO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
public class PublicationCreateDTO
{
    @JsonProperty("isbn13")
    private Long isbn13;
    @JsonProperty("title")
    private String title;
    @JsonProperty("publicationDate")
    private LocalDate publicationDate;
    @JsonProperty("pageCount")
    private Integer pageCount;
    @JsonProperty("genre")
    private String genre;
    @JsonProperty("authors")
    private List<String> authors;
    @JsonProperty("ebookUrl")
    private String ebookUrl;

    public PublicationCreateDTO(@JsonProperty("isbn13") Long isbn13, @JsonProperty("title") String title, @JsonProperty("publicationDate") LocalDate publicationDate, @JsonProperty("pageCount") Integer pageCount, @JsonProperty("genre") String genre, @JsonProperty("authors") List<String> authors)
    {
        this.isbn13 = isbn13;
        this.title = title;
        this.publicationDate = publicationDate;
        this.pageCount = pageCount;
        this.genre = genre;
        this.authors = authors;
    }

    @JsonCreator
    public PublicationCreateDTO(@JsonProperty("isbn13") Long isbn13, @JsonProperty("title") String title, @JsonProperty("publicationDate") LocalDate publicationDate, @JsonProperty("pageCount") Integer pageCount, @JsonProperty("genre") String genre, @JsonProperty("authors") List<String> authors, @JsonProperty("ebookUrl") String ebookUrl)
    {
        this.isbn13 = isbn13;
        this.title = title;
        this.publicationDate = publicationDate;
        this.pageCount = pageCount;
        this.genre = genre;
        this.authors = authors;
        this.ebookUrl = ebookUrl;
    }

}