package ca.kpu.info2413.library.backend.service;

import ca.kpu.info2413.library.backend.model.LibraryCard;
import ca.kpu.info2413.library.backend.repository.LibraryCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibraryCardService {

    @Autowired
    private LibraryCardRepository libraryCardRepository;

    public List<LibraryCard> findAll() {
        return libraryCardRepository.findAll();
    }

    public List<LibraryCard> findByCardNumber(Integer cardNumber) {
        return libraryCardRepository.findByCardNumber(cardNumber);
    }

    public Optional<LibraryCard> findFirstByCardNumber(Integer cardNumber) {
        return libraryCardRepository.findFirstByCardNumber(cardNumber);
    }

    public List<LibraryCard> findByAccountIdAccount(Integer accountIdAccount) {
        return libraryCardRepository.findByAccountAccountId(accountIdAccount);
    }

    public LibraryCard save(LibraryCard libraryCard) {
        return libraryCardRepository.save(libraryCard);
    }

    public void deleteByCardNumber(Integer cardNumber) {
        libraryCardRepository.deleteById(cardNumber);
    }
}