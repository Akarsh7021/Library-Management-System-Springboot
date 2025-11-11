package ca.kpu.info2413.library.backend.service;

import ca.kpu.info2413.library.backend.model.Account;
import ca.kpu.info2413.library.backend.model.BookCopy;
import ca.kpu.info2413.library.backend.model.LibraryCard;
import ca.kpu.info2413.library.backend.model.Publication;
import ca.kpu.info2413.library.backend.repository.AccountRepository;
import ca.kpu.info2413.library.backend.repository.BookCopyRepository;
import ca.kpu.info2413.library.backend.repository.LibraryCardRepository;
import ca.kpu.info2413.library.backend.repository.PublicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibraryCardService
{
    @Autowired
    LibraryCardRepository libraryCardRepository;


    public List<LibraryCard> findAll()
    {
        return libraryCardRepository.findAll();
    }

    public List<LibraryCard> findByCardNumber(Integer cardNumber)
    {
        return libraryCardRepository.findByCardNumber(cardNumber);
    }

    public LibraryCard save(LibraryCard libraryCard)
    {
        return libraryCardRepository.save(libraryCard);
    }

    public void deleteByCardNumber(Integer cardNumber)
    {
        libraryCardRepository.deleteById(cardNumber);
    }

    /// ////////////////////


    //redo using service later

    public Optional<LibraryCard> findByCardNumber(Integer cardNumber)
    {
        return libraryCardRepository.findByCardNumber(cardNumber);
    }

    public List<LibraryCard> findByAccountIdAccount(Integer accountIdAccount)
    {
        return libraryCardRepository.findByAccountIdAccount(accountIdAccount);
    }


}

