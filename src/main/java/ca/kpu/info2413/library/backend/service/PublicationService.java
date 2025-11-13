package ca.kpu.info2413.library.backend.service;

import ca.kpu.info2413.library.backend.DTO.PublicationDTO;
import ca.kpu.info2413.library.backend.model.Publication;
import ca.kpu.info2413.library.backend.model.PublicationAuthor;
import ca.kpu.info2413.library.backend.repository.PublicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublicationService {

    @Autowired
    private PublicationRepository publicationRepository;

    public List<PublicationDTO> findAll() {
        return publicationRepository.findAllFetchAuthors()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<PublicationDTO> findByIsbn13(Integer isbn13) {
        return publicationRepository.findById(isbn13)
                .map(this::toDTO)
                .map(List::of)
                .orElse(List.of());
    }

    public List<PublicationDTO> findByTitle(String title) {
        return publicationRepository.findByTitleContainingIgnoreCaseFetchAuthors(title)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<PublicationDTO> findByGenre(String genre) {
        return publicationRepository.findByGenreIgnoreCaseFetchAuthors(genre)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<PublicationDTO> findByAuthorName(String authorName) {
        return publicationRepository.findByAuthorNameLikeIgnoreCaseFetch(authorName)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<PublicationDTO> search(String type, String query) {
        if (type == null) type = "book";
        switch (type.toLowerCase()) {
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

    // Helper: convert entity -> DTO (safe for JSON)
    private PublicationDTO toDTO(Publication p) {
        List<String> authors = List.of();
        if (p.getPublicationAuthors() != null) {
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
                p.getPageCount()
        );
    }

    // Save/delete (optional)
    public Publication save(Publication publication) {
        return publicationRepository.save(publication);
    }

    public void deleteByIsbn13(Integer isbn13) {
        publicationRepository.deleteById(isbn13);
    }
}