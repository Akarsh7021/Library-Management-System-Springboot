package ca.kpu.info2413.library.backend.DTO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.List;

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

    @JsonCreator
    public PublicationCreateDTO(@JsonProperty("isbn13") Long isbn13, @JsonProperty("title") String title, @JsonProperty("publicationDate") LocalDate publicationDate, @JsonProperty("pageCount") Integer pageCount, @JsonProperty("genre") String genre, @JsonProperty("authors") List<String> authors)
    {
        this.isbn13 = isbn13;
        this.title = title;
        this.publicationDate = publicationDate;
        this.pageCount = pageCount;
        this.genre = genre;
        this.authors = authors;
    }

    public Long getIsbn13()
    {
        return isbn13;
    }

    public void setIsbn13(Long isbn13)
    {
        this.isbn13 = isbn13;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public LocalDate getPublicationDate()
    {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate)
    {
        this.publicationDate = publicationDate;
    }

    public Integer getPageCount()
    {
        return pageCount;
    }

    public void setPageCount(Integer pageCount)
    {
        this.pageCount = pageCount;
    }

    public String getGenre()
    {
        return genre;
    }

    public void setGenre(String genre)
    {
        this.genre = genre;
    }

    public List<String> getAuthors()
    {
        return authors;
    }

    public void setAuthors(List<String> authors)
    {
        this.authors = authors;
    }
}