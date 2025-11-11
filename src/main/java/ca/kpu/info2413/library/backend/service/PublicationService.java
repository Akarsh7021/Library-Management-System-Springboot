package ca.kpu.info2413.library.backend.service;

import ca.kpu.info2413.library.backend.model.Publication;
import ca.kpu.info2413.library.backend.repository.PublicationRepository;
import ca.kpu.info2413.library.backend.repository.LibraryCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PublicationService
{
    @Autowired
    PublicationRepository publicationRepository;
    @Autowired
    LibraryCardRepository libraryCardRepository;

    public List<Publication> findAll()
    {
        return publicationRepository.findAll();
    }

    public List<Publication> findByIsbn13(Integer isbn)
    {
        return publicationRepository.findByIsbn13(isbn);
    }

    public Publication save(Publication publication)
    {
        return publicationRepository.save(publication);
    }

    public void deleteByIsbn13(Integer isbn13)
    {
        publicationRepository.deleteById(isbn13);
    }

    /// ////////////////////


    //redo using service later
    public List<Publication> findByTitle(String title)
    {
        return publicationRepository.findByTitleContainsIgnoreCase(title);
    }

    public List<Publication> findByGenre(String genre)
    {
        return publicationRepository.findByGenre(genre);
    }


}
