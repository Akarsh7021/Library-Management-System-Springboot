package ca.kpu.info2413.library.backend.service;

import ca.kpu.info2413.library.backend.DTO.PublicationDTO;
import ca.kpu.info2413.library.backend.model.Author;
import ca.kpu.info2413.library.backend.model.Publication;
import ca.kpu.info2413.library.backend.model.PublicationAuthor;
import ca.kpu.info2413.library.backend.repository.AuthorRepository;
import ca.kpu.info2413.library.backend.repository.PublicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PublicationService
{

    @Autowired
    private PublicationRepository publicationRepository;

    @Autowired
    private AuthorRepository authorRepository;

    public List<PublicationDTO> findAll()
    {
        return publicationRepository.findAllFetchAuthors()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<PublicationDTO> findByIsbn13(Long isbn13)
    {
        return publicationRepository.findById(isbn13)
                .map(this::toDTO)
                .map(List::of)
                .orElse(List.of());
    }

    public Optional<Publication> findEntityByIsbn13(Long isbn13) {
        return publicationRepository.findById(isbn13);
    }

    public List<PublicationDTO> findByTitle(String title)
    {
        return publicationRepository.findByTitleContainingIgnoreCaseFetchAuthors(title)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<PublicationDTO> findByGenre(String genre)
    {
        return publicationRepository.findByGenreIgnoreCaseFetchAuthors(genre)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<PublicationDTO> findByAuthorName(String authorName)
    {
        return publicationRepository.findByAuthorNameLikeIgnoreCaseFetch(authorName)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // --- Unified search for catalog buttons ---
    public List<PublicationDTO> search(String type, String query)
    {
        if (type == null) type = "book";
        switch (type.toLowerCase())
        {
            case "book":
            case "title":
                return findByTitle(query);
            case "author":
                return findByAuthorName(query);
            case "genre":
                return findByGenre(query);
            default:
                return new ArrayList<>();
        }
    }

    // --- Homepage combined search: title, author, or ISBN ---
    public List<PublicationDTO> searchHomepage(String query)
    {
        String lowerQuery = query.toLowerCase();

        return publicationRepository.findAllFetchAuthors()
                .stream()
                .filter(pub ->
                        (pub.getTitle() != null && pub.getTitle().toLowerCase().contains(lowerQuery)) ||
                                (pub.getPublicationAuthors() != null &&
                                        pub.getPublicationAuthors().stream()
                                                .anyMatch(pa -> pa.getAuthor() != null &&
                                                        pa.getAuthor().getAuthorName().toLowerCase().contains(lowerQuery))) ||
                                (pub.getIsbn13() != null && pub.getIsbn13().toString().contains(lowerQuery))
                )
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public PublicationDTO toDTO(Publication p)
    {
        System.out.println("toDTO for " + p.getTitle() + " authors size: " + (p.getPublicationAuthors() != null ? p.getPublicationAuthors().size() : 0));
        List<String> authors = List.of();
        if (p.getPublicationAuthors() != null)
        {
            authors = p.getPublicationAuthors()
                    .stream()
                    .map(PublicationAuthor::getAuthor)
                    .filter(a -> a != null)
                    .map(a -> a.getAuthorName())
                    .distinct()
                    .collect(Collectors.toList());
        }

        return new PublicationDTO(
                p.getIsbn13(),
                p.getTitle(),
                authors,
                p.getGenre(),
                p.getPageCount(),
                p.getEbookUrl()
        );
    }

    public List<String> getGenres()
    {
        return publicationRepository.getGenres();
    }

    public List<PublicationDTO> recBookByGenre(String genre, Long isbn_13)
    {
        return publicationRepository.recBook(genre, isbn_13)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Publication save(Publication publication)
    {
        if (publication.getIsbn13() == null)
        {
            throw new IllegalArgumentException("ISBN-13 must be provided and cannot be null");
        }

        System.out.println("Saving publication: " + publication.getTitle() + " with ISBN: " + publication.getIsbn13());
        System.out.println("Authors list: " + (publication.getInputAuthors() != null ? publication.getInputAuthors() : "null"));

        if (publication.getInputAuthors() != null && !publication.getInputAuthors().isEmpty())
        {
            publication.setPublicationAuthors(new ArrayList<>());
            for (String authorName : publication.getInputAuthors())
            {
                System.out.println("Processing author: " + authorName);
                Author a;
                // Check if author exists by name
                List<Author> existing = authorRepository.findByAuthorName(authorName);
                if (!existing.isEmpty())
                {
                    System.out.println("Author exists, using existing: " + existing.get(0).getAuthorId());
                    a = existing.get(0);
                }
                else
                {
                    System.out.println("Author new, saving: " + authorName);
                    a = new Author();
                    a.setAuthorName(authorName);
                    a = authorRepository.save(a);
                }
                PublicationAuthor pa = new PublicationAuthor(a, publication);
                publication.getPublicationAuthors().add(pa);
                System.out.println("Created PublicationAuthor with id: " + pa.getId());
            }
        }

        // Clear the transient authors field to avoid JPA issues
        publication.setAuthors(null);

        return publicationRepository.save(publication);
    }

    public boolean isEbook(Publication publication)
    {
        return !publication.getEbookUrl().isEmpty();
    }

    public void deleteByIsbn13(Long isbn13)
    {
        publicationRepository.deleteById(isbn13);
    }
}