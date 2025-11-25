package ca.kpu.info2413.library.backend.controller;

import ca.kpu.info2413.library.backend.DTO.PublicationDTO;
import ca.kpu.info2413.library.backend.model.Author;
import ca.kpu.info2413.library.backend.model.Publication;
import ca.kpu.info2413.library.backend.model.PublicationAuthor;
import ca.kpu.info2413.library.backend.service.AuthorService;
import ca.kpu.info2413.library.backend.service.PublicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/publication")
@CrossOrigin(origins = "*")
public class PublicationController
{
    @Autowired
    private PublicationService publicationService;

    @Autowired
    private AuthorService authorService;

    @GetMapping
    public List<PublicationDTO> findAll()
    {
        return publicationService.findAll();
    }

    @GetMapping("/{isbn13}")
    public List<PublicationDTO> findByIsbn13(@PathVariable Integer isbn13)
    {
        return publicationService.findByIsbn13(isbn13);
    }

    /**
     * Create publication with authors. Expected JSON example:
     * {
     *   "isbn13": 1234567890123,
     *   "title": "My Book",
     *   "publicationDate": "2025-11-24",
     *   "pageCount": 200,
     *   "genre": "Fiction",
     *   "authors": ["Author One", "Author Two"]
     * }
     */
    @PostMapping
    public Publication create(@RequestBody PublicationInput input)
    {
        // Build Publication entity from input
        Publication publication = new Publication();
        publication.setIsbn13(input.getIsbn13());
        publication.setTitle(input.getTitle());
        publication.setPublicationDate(input.getPublicationDate());
        publication.setPageCount(input.getPageCount());
        publication.setGenre(input.getGenre());

        // Ensure collection exists
        if (publication.getPublicationAuthors() == null) {
            publication.setPublicationAuthors(new ArrayList<>());
        } else {
            publication.getPublicationAuthors().clear();
        }

        // If authors provided, ensure they exist (or create) and link them
        if (input.getAuthors() != null) {
            for (String rawName : input.getAuthors()) {
                if (rawName == null) continue;
                String name = rawName.trim();
                if (name.isEmpty()) continue;

                // Try find existing by exact name (service returns a List)
                List<Author> found = authorService.findByAuthorName(name);

                Author author;
                if (found != null && !found.isEmpty()) {
                    author = found.get(0); // reuse first match
                } else {
                    // Create new author
                    author = new Author();
                    author.setAuthorName(name);
                    author = authorService.save(author);
                }

                // Create PublicationAuthor link
                PublicationAuthor pa = new PublicationAuthor();
                pa.setAuthor(author);
                pa.setPublication(publication);

                publication.getPublicationAuthors().add(pa);
            }
        }

        return publicationService.save(publication);
    }

    /**
     * Update publication. This implementation tries to replace data and reattach authors.
     * IMPORTANT: if you have Hibernate orphan/collection issues on update, the safer approach
     * is to implement the mutation logic inside PublicationService in a @Transactional method
     * that fetches the managed Publication entity and mutates the existing collection (clear/add),
     * instead of replacing the collection here. If you want, I can move this logic to the service.
     */
    @PutMapping
    public Publication update(@RequestBody PublicationInput input)
    {
        // This mirrors create() behavior: build Publication object and attach authors,
        // then delegate to service.save(...) which should persist or merge.
        Publication publication = new Publication();
        publication.setIsbn13(input.getIsbn13());
        publication.setTitle(input.getTitle());
        publication.setPublicationDate(input.getPublicationDate());
        publication.setPageCount(input.getPageCount());
        publication.setGenre(input.getGenre());

        if (publication.getPublicationAuthors() == null) {
            publication.setPublicationAuthors(new ArrayList<>());
        } else {
            publication.getPublicationAuthors().clear();
        }

        if (input.getAuthors() != null) {
            for (String rawName : input.getAuthors()) {
                if (rawName == null) continue;
                String name = rawName.trim();
                if (name.isEmpty()) continue;

                List<Author> found = authorService.findByAuthorName(name);

                Author author;
                if (found != null && !found.isEmpty()) {
                    author = found.get(0);
                } else {
                    author = new Author();
                    author.setAuthorName(name);
                    author = authorService.save(author);
                }

                PublicationAuthor pa = new PublicationAuthor();
                pa.setAuthor(author);
                pa.setPublication(publication);

                publication.getPublicationAuthors().add(pa);
            }
        }

        return publicationService.save(publication);
    }

    @DeleteMapping("/{isbn13}")
    public void deleteById(@PathVariable Integer isbn13)
    {
        publicationService.deleteByIsbn13(isbn13);
    }

    // --- Catalog button search ---
    @GetMapping("/search")
    public List<PublicationDTO> searchPublications(
            @RequestParam(defaultValue = "book") String type,
            @RequestParam String query
    )
    {
        return publicationService.search(type, query);
    }

    // --- Homepage combined search ---
    @GetMapping("/searchHomepage")
    public List<PublicationDTO> searchHomepage(@RequestParam String query)
    {
        return publicationService.searchHomepage(query);
    }

    // ---------- Helper DTO ----------
    // A small internal DTO to receive the client JSON. You can move this to its own file if you prefer.
    public static class PublicationInput {
        private Integer isbn13;
        private String title;
        private java.time.LocalDate publicationDate;
        private Integer pageCount;
        private String genre;
        private List<String> authors;

        public Integer getIsbn13() { return isbn13; }
        public void setIsbn13(Integer isbn13) { this.isbn13 = isbn13; }

        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }

        public java.time.LocalDate getPublicationDate() { return publicationDate; }
        public void setPublicationDate(java.time.LocalDate publicationDate) { this.publicationDate = publicationDate; }

        public Integer getPageCount() { return pageCount; }
        public void setPageCount(Integer pageCount) { this.pageCount = pageCount; }

        public String getGenre() { return genre; }
        public void setGenre(String genre) { this.genre = genre; }

        public List<String> getAuthors() { return authors; }
        public void setAuthors(List<String> authors) { this.authors = authors; }
    }
}