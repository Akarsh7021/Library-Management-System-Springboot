package ca.kpu.info2413.library.backend.service;

import ca.kpu.info2413.library.backend.model.BookCopy;
import ca.kpu.info2413.library.backend.model.Publication;
import ca.kpu.info2413.library.backend.repository.BookCopyRepository;
import ca.kpu.info2413.library.backend.repository.PublicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookCopyService
{
    @Autowired
    BookCopyRepository bookCopyRepository;
    @Autowired
    PublicationRepository publicationRepository;

    public List<BookCopy> findAll()
    {
        return bookCopyRepository.findAll();
    }

    public Optional<BookCopy> findBySerialBarcode(Integer serialBarcode)
    {
        return bookCopyRepository.findBySerialBarcode(serialBarcode);
    }

    public BookCopy save(BookCopy bookCopy)
    {
        return bookCopyRepository.save(bookCopy);
    }

    public void deleteBySerialBarcode(Integer serialBarcode)
    {
        bookCopyRepository.deleteById(serialBarcode);
    }

    /// ////////////////////


    public List<BookCopy> findByPublication(Publication publication)
    {
        return bookCopyRepository.findByPublication(publication);
    }

    public List<BookCopy> findByIsbn13(Integer isbn13)
    {
        return findByPublication(publicationRepository.findByIsbn13(isbn13).orElse(null));
    }
}
