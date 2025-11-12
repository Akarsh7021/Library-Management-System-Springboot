package ca.kpu.info2413.library.backend.service;

import ca.kpu.info2413.library.backend.model.LibraryCard;
import ca.kpu.info2413.library.backend.repository.LibraryCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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


    public List<LibraryCard> findByAccountIdAccount(Integer accountIdAccount)
    {
        return libraryCardRepository.findByAccountIdAccount(accountIdAccount);
    }


}

